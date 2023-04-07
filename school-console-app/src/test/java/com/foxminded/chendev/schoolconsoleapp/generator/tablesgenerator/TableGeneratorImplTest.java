package com.foxminded.chendev.schoolconsoleapp.generator.tablesgenerator;

import com.foxminded.chendev.schoolconsoleapp.dao.DBConnector;
import com.foxminded.chendev.schoolconsoleapp.dao.DataBaseRuntimeException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@ExtendWith(MockitoExtension.class)
class TableGeneratorImplTest {

    @Mock
    private DBConnector dbConnector;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @InjectMocks
    private TableGeneratorImpl tableGenerator;

    private final String creationQuery = "CREATE TABLE test_table (id INT PRIMARY KEY);";

    @Test
    void generateTable_executesCreationQuery() throws SQLException {

        Mockito.when(dbConnector.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(creationQuery)).thenReturn(preparedStatement);

        tableGenerator.generateTable(creationQuery);

        Mockito.verify(dbConnector).getConnection();
        Mockito.verify(connection).prepareStatement(creationQuery);
        Mockito.verify(preparedStatement).execute();
    }

    @Test
    void generateTable_throwsRuntimeException_onSQLException() throws SQLException {

        Mockito.when(dbConnector.getConnection()).thenReturn(connection);
        Mockito.when(connection.prepareStatement(creationQuery)).thenReturn(preparedStatement);
        Mockito.doThrow(SQLException.class).when(preparedStatement).execute();

        org.junit.jupiter.api.Assertions.assertThrows(DataBaseRuntimeException.class, () -> tableGenerator.generateTable(creationQuery));
    }
}
