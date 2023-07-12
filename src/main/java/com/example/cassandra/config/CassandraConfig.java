package com.example.cassandra.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableCassandraRepositories(basePackages = "com.example.cassandra.repository")
@EntityScan(basePackages = "com.example.cassandra.domain")
@EnableTransactionManagement
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Override
    public String getContactPoints() {
        return "localhost";
    }

    @Override
    protected String getKeyspaceName() {
        return "for_test";
    }
}

