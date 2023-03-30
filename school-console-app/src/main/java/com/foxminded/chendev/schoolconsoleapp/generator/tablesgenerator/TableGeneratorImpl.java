package com.foxminded.chendev.schoolconsoleapp.generator.tablesgenerator;

import com.foxminded.chendev.schoolconsoleapp.dao.DBConnector;
import com.foxminded.chendev.schoolconsoleapp.dao.DataBaseRuntimeException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TableGeneratorImpl implements TableGenerator{

    private final DBConnector connector;

    public TableGeneratorImpl(DBConnector connector) {
        this.connector = connector;
    }

    @Override
    public void generateTable(String creationQuery) {

        try(Connection connection = connector.getConnection();
            PreparedStatement preparedStatement = connection
                    .prepareStatement(creationQuery)) {

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }
}
