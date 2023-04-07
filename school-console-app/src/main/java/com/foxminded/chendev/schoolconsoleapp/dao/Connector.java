package com.foxminded.chendev.schoolconsoleapp.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface Connector {

    Connection getConnection() throws SQLException;

}
