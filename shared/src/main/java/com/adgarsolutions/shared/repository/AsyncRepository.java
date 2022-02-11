package com.adgarsolutions.shared.repository;

import com.adgarsolutions.shared.exception.ItemNotFoundInDatabaseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AsyncRepository<T extends Identifiable<ID>, ID> implements Closeable {

    private final static Logger LOG = LoggerFactory.getLogger(AsyncRepository.class);

    private Connection connection;

    protected AsyncRepository(Connection connection) {
        this.connection = connection;
    }

    protected abstract T getNextItemFromRecordSet(final ResultSet rs) throws SQLException;

    protected abstract PreparedStatement getPreparedStatement(String ctx, Connection connection, Object... args)  throws SQLException;

    public Mono<? extends T> save(@NonNull T entity) {
        return null;
    }

    public Mono<? extends T> update(@NonNull T entity) {
        return null;
    }

    public Flux<? extends T> updateAll(@NonNull Iterable<T> entities) {
        return null;
    }

    public Flux<? extends T> saveAll(@NonNull Iterable<T> entities) {
        return null;
    }

    public Mono<? extends T> findById(@NonNull String s) {
        try {
            PreparedStatement ps = getPreparedStatement("findById", this.connection, s);
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

    public Mono<Boolean> existsById(@NonNull String s) {
       return null;
    }

    public Flux<? extends Iterable<T>> findAll() {
        return null;
    }

    public Mono<Long> count() {
        return null;
    }

    public Mono<Long> deleteById(@NonNull String s) {
        return null;
    }

    public Mono<Boolean> delete(@NonNull T entity) {
        return null;
    }

    public Mono<Long> deleteAll(@NonNull Iterable<? extends T> entities) {
        return null;
    }

    public Mono<Long> deleteAll() {
        return null;
    }

    private PreparedStatement getPreparedStatement(String query) throws SQLException {
        return connection.prepareStatement(query);
    }

    @Override
    public void close() throws IOException {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                LOG.info("Connection closed SUCCESS");
            } catch (Exception ex) {
                LOG.warn("Problem closing connection: {}", ex.getMessage());
            }
        }
    }
}