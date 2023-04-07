package com.foxminded.chendev.schoolconsoleapp.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBConnector implements Connector {

    private final HikariDataSource dataSource;

    public DBConnector(String fileConfigurationName) {

        ResourceBundle resource = ResourceBundle.getBundle(fileConfigurationName);
        String url = resource.getString("db.url");
        String user = resource.getString("db.user");
        String password = resource.getString("db.password");

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(user);
        config.setPassword(password);

        dataSource = new HikariDataSource(config);
    }

    @Override
    public Connection getConnection() throws SQLException {

        return dataSource.getConnection();
    }
}
