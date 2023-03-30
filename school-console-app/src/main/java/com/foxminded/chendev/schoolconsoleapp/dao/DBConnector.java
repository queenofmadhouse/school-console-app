package com.foxminded.chendev.schoolconsoleapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class DBConnector implements Connector {
    
    private final String url;
    private final String user;
    private final String password;
    
    public DBConnector(String fileConfigurationName) {
        ResourceBundle resource = ResourceBundle.getBundle(fileConfigurationName);
        this.url = resource.getString("db.url");
        this.user = resource.getString("db.user");
        this.password = resource.getString("db.password");
    }

    @Override
    public Connection getConnection() {
        try {
            return DriverManager.getConnection(url, user, password);
        } catch(SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }
}
