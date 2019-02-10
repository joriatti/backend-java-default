package com.backend.api.v1.setup;

import com.backend.security.TokenAuthenticationService;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@TestConfiguration
public class IntegrationTestConfiguration {

    MySqlDatabaseTest dbTest;

    @Autowired
    public IntegrationTestConfiguration(@Value("${backend.jwt.signing-key}") String jwtKey) {
        dbTest = new MySqlDatabaseTest();
        System.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

        TokenAuthenticationService.setSecret(jwtKey);
    }

    private DriverManagerDataSource driverManagerDataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource(
                dbTest.getJdbcUrl(),
                dbTest.getUsername(),
                dbTest.getPassword());
        driverManagerDataSource.setDriverClassName(dbTest.getDriverClassName());
        return driverManagerDataSource;
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setMaximumPoolSize(5);
        hikariConfig.setDataSource(driverManagerDataSource());
        hikariConfig.setConnectionTimeout(2147483647L);
        hikariConfig.setAutoCommit(true);
        HikariDataSource ds = new HikariDataSource(hikariConfig);
        return ds;
    }
}