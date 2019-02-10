package com.backend.api.v1.setup;

import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;

public class MySqlDatabaseTest {

    JdbcDatabaseContainer jdbcDatabaseContainer;
    MySQLContainer mysql;

    public MySqlDatabaseTest() {
        mysql = new MySQLContainer("mysql:5.6")
                .withConfigurationOverride("mysql_conf_override");
        mysql.start();
        setJdbcDatabaseContainer(mysql);
    }

    public String getDriverClassName() {
        return mysql.getDriverClassName();
    }

    private void setJdbcDatabaseContainer(JdbcDatabaseContainer jdbcDatabaseContainer) {
        this.jdbcDatabaseContainer = jdbcDatabaseContainer;
    }

    public String getJdbcUrl() {
        String url = jdbcDatabaseContainer.getJdbcUrl() + getUrlParams();
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        System.out.println("JdbcUrl = " + url);
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
        return url;
    }

    public String getUsername() {
        return jdbcDatabaseContainer.getUsername();
    }

    public String getPassword() {
        return jdbcDatabaseContainer.getPassword();
    }

    /**
     * useSSL=false,useLegacyDatetimeCode=false
     *
     * @return
     */
    private String getUrlParams() {
        String params = System.getProperty("dbUrlParams");
        StringBuilder urlParams = new StringBuilder();
        if (params != null && !params.isEmpty()) {
            String[] param = params.split(",");
            for (int i = 0; i < param.length; i++) {
                urlParams.append(i == 0 ? "?" : i <= param.length - 1 ? "&" : "");
                urlParams.append(param[i]);
            }
        }
        return urlParams.toString();
    }
}