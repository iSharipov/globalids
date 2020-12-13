package io.github.isharipov.globalids.test;

import org.testcontainers.containers.PostgreSQLContainer;

public class PostgreTestContainer extends PostgreSQLContainer<PostgreTestContainer> {
    public PostgreTestContainer() {
        super("postgres:13.1");
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("spring.datasource.url", getJdbcUrl());
        System.setProperty("spring.datasource.username", getUsername());
        System.setProperty("spring.datasource.password", getPassword());
    }

}