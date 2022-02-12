package com.adgarsolutions.config;

import com.adgarsolutions.shared.repository.DbContext;
import io.micronaut.context.event.BeanContextEvent;
import io.micronaut.context.event.ShutdownEvent;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.runtime.event.annotation.EventListener;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Singleton
public class DbContextImpl implements DbContext {

    private final static Logger LOG = LoggerFactory.getLogger(DbContextImpl.class);

    private final DbSourceConfiguration dbSourceConfiguration;

    private final List<Connection> connections;
    private int connectionIdx;

    public DbContextImpl(DbSourceConfiguration dbSourceConfiguration) {
        this.dbSourceConfiguration = dbSourceConfiguration;
        this.connections = new ArrayList<>();
    }

    @EventListener
    public void onStartup(BeanContextEvent beanContextEvent) throws SQLException {
        if (beanContextEvent instanceof StartupEvent) {
            LOG.info("Creating SQL connections (requested {})", this.dbSourceConfiguration.getConnections());
            for (var i=0; i < this.dbSourceConfiguration.getConnections(); ++i) {
                this.connections.add(createConnection(this.dbSourceConfiguration));
            }
        }

        if (beanContextEvent instanceof ShutdownEvent) {
            close();
        }
    }

    private Connection createConnection(DbSourceConfiguration dbSourceConfiguration) throws SQLException {
        String connectionUrl = String.format("%s?currentSchema=%s&user=%s&password=%s", // TODO: SSL
                dbSourceConfiguration.getUrl(), dbSourceConfiguration.getDefaultSchema(), dbSourceConfiguration.getUser(),
                dbSourceConfiguration.getPassword());

        try {
            var connection = DriverManager.getConnection(connectionUrl);
            LOG.info("Connection created SUCCESS");
            return connection;
        } catch (Exception ex) {
            LOG.error("FAILED CREATING CONNECTION: {}", ex.getMessage());
            throw ex;
        }
    }

    @Override
    public <T> PreparedStatement getPreparedStatementIterable(String query, Iterable<T> iter) throws SQLException {
        // Step 1. Get next connection (round robin)
        var join = String.join(",", StreamSupport.stream(iter.spliterator(), false)
                .map(v -> "?")
                .collect(Collectors.toList()));
        var stmt = String.format("select * from smplc.order where id in (%s)", join);
        PreparedStatement ps = getNextConnection().prepareStatement(stmt);
        var idx = 1;
        for (T p : iter) {
            ps.setObject(idx++, p);
        }

        return ps;
    }

    @Override
    public PreparedStatement getPreparedStatement(String query, Object... params) throws SQLException {
        // Step 1. Get next connection (round robin)
        var ret = getNextConnection().prepareStatement(query);
        var idx = 1;
        for (Object p : params) {
            ret.setObject(idx++, p);
        }

        return ret;
    }

    private synchronized Connection getNextConnection() {
        if (this.connectionIdx >= this.connections.size()) {
            this.connectionIdx = 0;
        }

        var ret = this.connections.get(this.connectionIdx++);

        return ret;
    }

    @Override
    public void close() {
        for (Connection connection : this.connections) {
            try {
                connection.close();
                LOG.info("Connection closed SUCCESS");
            } catch (Exception ex) {
                LOG.warn("Connection closed FAILED: {}", ex.getMessage());
            }
        }

        this.connections.clear();
    }
}
