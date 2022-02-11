package com.adgarsolutions;

import com.adgarsolutions.config.DbSourceConfiguration;
import io.micronaut.context.annotation.Bean;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;
import jdk.jfr.Name;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Factory
public class SqlConnectionFactory {

    private final static Logger LOG = LoggerFactory.getLogger(SqlConnectionFactory.class);

    private Connection connection;

    @Bean
    @Primary
    public Connection getSharedConnection(DbSourceConfiguration dbSourceConfiguration) throws SQLException {
       return getConnection(dbSourceConfiguration, false);
    }

    private synchronized Connection getConnection(
            DbSourceConfiguration dbSourceConfiguration,
            boolean createNewOnEachRequest) throws SQLException
    {
        if (createNewOnEachRequest || this.connection == null) {
            createConnection(dbSourceConfiguration);
        }

        return this.connection;
    }

    @Bean
    public Connection getConnectionPerRequest(DbSourceConfiguration dbSourceConfiguration) throws SQLException {
        return getConnection(dbSourceConfiguration, true);
    }

    private Connection createConnection(DbSourceConfiguration dbSourceConfiguration) throws SQLException {
        String connectionUrl = String.format("%s?currentSchema=%s&user=%s&password=%s", // TODO: SSL
                dbSourceConfiguration.getUrl(), dbSourceConfiguration.getDefaultSchema(), dbSourceConfiguration.getUser(),
                dbSourceConfiguration.getPassword());

        try {
            this.connection = DriverManager.getConnection(connectionUrl);
            LOG.info("Connection created SUCCESS");
            return this.connection;
        } catch (Exception ex) {
            LOG.error("FAILED CREATING CONNECTION: {}", ex.getMessage());
            throw ex;
        }
    }
}
