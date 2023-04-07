package com.foxminded.chendev.schoolconsoleapp.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class DBConnectorTest {

    private Connector connector;

    @BeforeEach
    public void setUp() {
        connector = new DBConnector("application-test");
    }

    @Test
    void shouldGetConnection() throws SQLException {
        try (Connection connection = connector.getConnection()) {
            assertNotNull(connection);
        }
    }
}
