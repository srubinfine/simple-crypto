package com.adgarsolutions;

import com.adgarsolutions.config.DbSourceConfiguration;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Factory
public class SqlConnectionFactory {

    private final static Logger LOG = LoggerFactory.getLogger(SqlConnectionFactory.class);

    @Bean
    public Connection getConnection(DbSourceConfiguration dbSourceConfiguration) throws SQLException {
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
}
