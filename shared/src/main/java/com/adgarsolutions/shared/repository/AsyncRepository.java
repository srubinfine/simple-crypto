package com.adgarsolutions.shared.repository;

import com.adgarsolutions.shared.exception.EmptyDatabaseCollectionException;
import com.adgarsolutions.shared.exception.ItemNotFoundInDatabaseException;
import io.micronaut.core.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public abstract class AsyncRepository<T extends Identifiable<ID>, ID> {

    private final static Logger LOG = LoggerFactory.getLogger(AsyncRepository.class);

    private final DbContext dbContext;
    private RepositoryQueriesContainer repositoryQueriesContainer;

    enum QueryBinderState {
        WAIT,
        IN_CRUD,
        IN_STORED_PROC
    }

    private QueryBinderState queryBinderState = QueryBinderState.WAIT;

    protected AsyncRepository(DbContext dbContext) {

        this.dbContext = dbContext;

        configureQueryBinder();
    }

    protected abstract T getNextItemFromRecordSet(final ResultSet rs) throws SQLException;

    public Mono<ID> save(@NonNull T entity) {
        return null;
    }

    public Mono<? extends T> update(@NonNull T updatedEntity) {
        return null;
    }

    // Count of updated entities
    public Flux<? extends T> updateAll(@NonNull Iterable<T> updatedEntities) {
        return null;
    }

    public Flux<ID> saveAll(@NonNull Iterable<T> entities) {
        return null;
    }

    public Mono<? extends T> findById(@NonNull ID s) {
        PreparedStatement ps;
        try {
            ps = dbContext.getPreparedStatement(repositoryQueriesContainer.getFindById(), s);
            var rs = ps.executeQuery();
            return Mono.create(c -> {
                try {
                    var hasRecords = rs.next();
                    if (!hasRecords) {
                        c.error(new ItemNotFoundInDatabaseException("Item with id '" + s + "' not found in db"));
                    } else {
                        T nxt = getNextItemFromRecordSet(rs);
                        c.success(nxt);
                    }
                } catch (Exception ex) {
                    c.error(ex);
                }
            });
        } catch (Exception ex) {
            return Mono.error(ex);
        }
    }

    public Mono<Boolean> existsById(@NonNull ID s) {
        PreparedStatement ps;
        try {
            ps = dbContext.getPreparedStatement(repositoryQueriesContainer.getFindById(), s);
            var rs = ps.executeQuery();
            return Mono.create(c -> {
                try {
                    var hasRecords = rs.next();
                    if (!hasRecords) {
                        c.success(true);
                    } else {
                        c.success(false);
                    }
                } catch (Exception ex) {
                    c.error(ex);
                }
            });
        } catch (Exception ex) {
            return Mono.error(ex);
        }
    }

    public Flux<? extends T> findAll(@NonNull Iterable<ID> ids) {
        try {
            var ps = dbContext.getPreparedStatementIterable(repositoryQueriesContainer.getFindAll(), ids);
            var rs = ps.executeQuery();

            return Flux.create(c -> {
                try {
                    var hasRecords = rs.next();
                    if (!hasRecords) {
                        c.error(new EmptyDatabaseCollectionException("No records found in DB for ids: " + ids));
                    } else {
                        T nxt = getNextItemFromRecordSet(rs);
                        c.next(nxt);
                        while(rs.next()) {
                            nxt = getNextItemFromRecordSet(rs);
                            c.next(nxt);
                        }
                        c.complete();
                    }
                } catch (Exception ex) {
                    c.error(ex);
                }
            });
        } catch (Exception ex) {
            return Flux.error(ex);
        }
    }

    public Mono<Long> count() {
        PreparedStatement ps;
        try {
            ps = dbContext.getPreparedStatement(repositoryQueriesContainer.getCount());
            var rs = ps.executeQuery();
            return Mono.create(c -> {
                try {
                    rs.next();
                    final var count = rs.getLong("count");
                    c.success(count);
                } catch (Exception ex) {
                    c.error(ex);
                }
            });
        } catch (Exception ex) {
            return Mono.error(ex);
        }
    }

    public Mono<Long> deleteById(@NonNull ID s) {
        return null;
    }

    public Mono<Boolean> delete(@NonNull ID entity) {
        return null;
    }

    public Mono<Long> deleteAll(@NonNull Iterable<ID> entities) {
        return null;
    }

    private void configureQueryBinder() {
        var binders = this.getClass().getAnnotationsByType(QueryBinder.class);
        if (binders == null || binders.length <= 0) {
            throw new RuntimeException("QueryBinder annotation with a valid 'file' property must be defined on any Repository class");
        }

        var f = binders[0].file();
        if (StringUtils.isEmpty(f)) {
            throw new RuntimeException("QueryBinder annotation: file property must be overwritten");
        }

        try {
            String fileName = (f.toUpperCase(Locale.ROOT).endsWith(".YML") || f.toUpperCase(Locale.ROOT).endsWith(".YAML"))
                    ? f : f + ".yml";
            var url = this.getClass().getClassLoader().getResource(Path.of("db", "repository", fileName).toString());
            if (url == null) {
                throw new RuntimeException("QueryBinder annotation: " + fileName + "(.yml) or (.yaml) does not exist in resources/db/repository");
            }
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            repositoryQueriesContainer = new RepositoryQueriesContainer();
            String nxt = null;
            while (null != (nxt = in.readLine())) {
                if (nxt.equalsIgnoreCase("CRUD:")) {
                    queryBinderState = QueryBinderState.IN_CRUD;
                }
                else if (nxt.equalsIgnoreCase("STORED-PROCEDURES:")) {
                    queryBinderState = QueryBinderState.IN_STORED_PROC;
                } else if (nxt.endsWith(":")) {
                    queryBinderState = QueryBinderState.WAIT;
                } else {
                    switch (queryBinderState) {
                        case IN_CRUD:
                            processCrud(nxt, repositoryQueriesContainer);
                            break;
                        case IN_STORED_PROC:
                            processSp(nxt, repositoryQueriesContainer);
                            break;
                    }
                }
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private void processCrud(String line, RepositoryQueriesContainer container) {
        var tokens = line.split(":");
        if (tokens != null && tokens.length == 2) {
            switch(tokens[0].trim().toUpperCase(Locale.ROOT)) {
                case "FINDALL":
                    container.setFindAll(tokens[1].trim());
                    break;
                case "FINDBYID":
                    container.setFindById(tokens[1].trim());
                    break;
                case "COUNT":
                    container.setCount(tokens[1].trim());
                    break;
                default:
                    break;
            }
        }
    }

    public <U> Flux<U> fetchResultMany(String storedProcName, Object... params) {
        return null;
    }

    public <U> Mono<U> fetchResultOne(String storedProcName, Object... params) {
        return null;
    }

    private void processSp(String line, RepositoryQueriesContainer container) {
        container.getStoredProcs().add(line.trim());
    }
}