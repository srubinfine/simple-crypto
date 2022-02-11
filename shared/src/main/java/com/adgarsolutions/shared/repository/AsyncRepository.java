package com.adgarsolutions.shared.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.annotation.NonNull;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;

public class AsyncRepository<T extends Identifiable<ID>, ID> implements Closeable {

    private final static Logger LOG = LoggerFactory.getLogger(AsyncRepository.class);

    private Connection connection;

    protected AsyncRepository(Connection connection) {
        this.connection = connection;
    }

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
        return null;
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