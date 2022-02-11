package com.adgarsolutions.repository;

import io.micronaut.core.annotation.Order;
import io.micronaut.data.repository.async.AsyncCrudRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.util.concurrent.CompletableFuture;

@Singleton
public class AsyncOrderRepository implements AsyncCrudRepository<Order, String>, Closeable {

    private final static Logger LOG = LoggerFactory.getLogger(AsyncOrderRepository.class);

    private Connection connection;

    public AsyncOrderRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public <S extends Order> CompletableFuture<S> save(@Valid @NotNull S entity) {
        return null;
    }

    @Override
    public <S extends Order> CompletableFuture<S> update(@Valid @NotNull S entity) {
        return null;
    }

    @Override
    public <S extends Order> CompletableFuture<? extends Iterable<S>> updateAll(@Valid @NotNull Iterable<S> entities) {
        return null;
    }

    @Override
    public <S extends Order> CompletableFuture<? extends Iterable<S>> saveAll(@Valid @NotNull Iterable<S> entities) {
        return null;
    }

    @Override
    public CompletableFuture<Order> findById(@NotNull String s) {
        return null;
    }

    @Override
    public CompletableFuture<Boolean> existsById(@NotNull String s) {
        return null;
    }

    @Override
    public CompletableFuture<? extends Iterable<Order>> findAll() {
        return null;
    }

    @Override
    public CompletableFuture<Long> count() {
        return null;
    }

    @Override
    public CompletableFuture<Void> deleteById(@NotNull String s) {
        return null;
    }

    @Override
    public CompletableFuture<Void> delete(@NotNull Order entity) {
        return null;
    }

    @Override
    public CompletableFuture<Void> deleteAll(@NotNull Iterable<? extends Order> entities) {
        return null;
    }

    @Override
    public CompletableFuture<Void> deleteAll() {
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
