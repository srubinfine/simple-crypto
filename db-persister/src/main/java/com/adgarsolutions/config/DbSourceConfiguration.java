package com.adgarsolutions.config;

import io.micronaut.context.annotation.ConfigurationProperties;

@ConfigurationProperties("flyway.datasources.default")
public class DbSourceConfiguration {
    private Boolean enabled;
    private Boolean cleanSchema;
    private String url;
    private String user;
    private String password;
    private String schemas;
    private String defaultSchema;
    private Integer connections;

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getCleanSchema() {
        return cleanSchema;
    }

    public void setCleanSchema(Boolean cleanSchema) {
        this.cleanSchema = cleanSchema;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSchemas() {
        return schemas;
    }

    public void setSchemas(String schemas) {
        this.schemas = schemas;
    }

    public String getDefaultSchema() {
        return defaultSchema;
    }

    public void setDefaultSchema(String defaultSchema) {
        this.defaultSchema = defaultSchema;
    }

    public Integer getConnections() {
        return connections;
    }

    public void setConnections(Integer connections) {
        this.connections = connections;
    }

    @Override
    public String toString() {
        return "DbSourceConfiguration{" +
                "enabled=" + enabled +
                ", cleanSchema=" + cleanSchema +
                ", url='" + url + '\'' +
                ", user='" + user + '\'' +
                ", password='" + password + '\'' +
                ", schemas='" + schemas + '\'' +
                ", defaultSchema='" + defaultSchema + '\'' +
                ", connections=" + connections +
                '}';
    }
}


