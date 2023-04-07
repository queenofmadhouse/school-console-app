package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.DBConnector;
import com.foxminded.chendev.schoolconsoleapp.dao.DataBaseRuntimeException;
import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static com.foxminded.chendev.schoolconsoleapp.dao.impl.AbstractCrudDao.LONG_CONSUMER;
import static com.foxminded.chendev.schoolconsoleapp.dao.impl.AbstractCrudDao.STING_CONSUMER;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GroupDaoImplTest {

    @Mock
    private DBConnector mockDBConnector;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private GroupDaoImpl mockGroupDao;

    private final long mockParam = 123;

    @Test
    void saveShouldCatchSQLException() throws SQLException {

        when(mockDBConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doThrow(SQLException.class).when(mockStatement).execute();

        assertThrows(DataBaseRuntimeException.class, () -> mockGroupDao.save(Group.builder().build()));
    }

    @Test
    void findByIdShouldCatchSQLException() throws SQLException {

        when(mockDBConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenThrow(SQLException.class);

        assertThrows(DataBaseRuntimeException.class, () -> mockGroupDao.findById(1L));
    }

    @Test
    void findAllShouldCatchSQLException() throws SQLException {

        when(mockDBConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenThrow(SQLException.class);

        assertThrows(DataBaseRuntimeException.class, () -> mockGroupDao.findAll());
    }

    @Test
    void updateShouldCatchSQLException() throws SQLException {

        when(mockDBConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doThrow(SQLException.class).when(mockStatement).executeUpdate();

        assertThrows(DataBaseRuntimeException.class, () -> mockGroupDao.update(Group.builder().build()));
    }

    @Test
    void deleteByIdShouldCatchSQLException() throws SQLException {

        when(mockDBConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doThrow(SQLException.class).when(mockStatement).executeUpdate();

        assertThrows(DataBaseRuntimeException.class, () -> mockGroupDao.deleteByID(1L));
    }

    @Test
    void testFindGroupsWithLessOrEqualStudentsShouldCatchSQLException() throws SQLException {

        when(mockDBConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenThrow(SQLException.class);

        assertThrows(DataBaseRuntimeException.class, () -> mockGroupDao.findGroupsWithLessOrEqualStudents(mockParam));
    }

    @Test
    void testStringConsumerShouldCatchSQLException() throws SQLException {

        doThrow(SQLException.class).when(mockStatement).setString(anyInt(), anyString());

        assertThrows(DataBaseRuntimeException.class, () -> STING_CONSUMER.accept(mockStatement, "test"));
    }

    @Test
    void testSetLongParamShouldCatchSQLException() throws SQLException {


        doThrow(SQLException.class).when(mockStatement).setLong(1, mockParam);

        assertThrows(DataBaseRuntimeException.class, () -> LONG_CONSUMER.accept(mockStatement, mockParam));
    }
}

