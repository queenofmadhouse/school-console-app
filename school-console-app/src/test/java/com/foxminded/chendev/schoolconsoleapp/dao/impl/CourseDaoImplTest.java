package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.DBConnector;
import com.foxminded.chendev.schoolconsoleapp.dao.DataBaseRuntimeException;
import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.StudentCourseRelation;
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
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CourseDaoImplTest {

    @Mock
    private DBConnector mockDBConnector;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private CourseDaoImpl mockCourseDao;

    @Test
    void save_throws_DataBaseRuntimeException() throws SQLException {

        when(mockDBConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doThrow(SQLException.class).when(mockStatement).execute();

        assertThrows(DataBaseRuntimeException.class, () -> mockCourseDao.save(Course.builder().build()));
    }

    @Test
    void findByIdShouldCatchSQLException() throws SQLException {

        when(mockDBConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenThrow(SQLException.class);

        assertThrows(DataBaseRuntimeException.class, () -> mockCourseDao.findById(1L));
    }

    @Test
    void findAllShouldCatchSQLException() throws SQLException {

        when(mockDBConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenThrow(SQLException.class);

        assertThrows(DataBaseRuntimeException.class, () -> mockCourseDao.findAll());
    }

    @Test
    void updateShouldCatchSQLException() throws SQLException {

        when(mockDBConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doThrow(SQLException.class).when(mockStatement).executeUpdate();

        assertThrows(DataBaseRuntimeException.class, () -> mockCourseDao.update(Course.builder().build()));
    }

    @Test
    void deleteByIdShouldCatchSQLException() throws SQLException {

        when(mockDBConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doThrow(SQLException.class).when(mockStatement).executeUpdate();

        assertThrows(DataBaseRuntimeException.class, () -> mockCourseDao.deleteByID(1L));
    }

    @Test
    void testStringConsumerShouldCatchSQLException() throws SQLException {

        doThrow(SQLException.class).when(mockStatement).setString(anyInt(), anyString());

        assertThrows(DataBaseRuntimeException.class, () -> STING_CONSUMER.accept(mockStatement, "test"));
    }

    @Test
    void testSetLongParamShouldCatchSQLException() throws SQLException {

        long mockParam = 123;

        doThrow(SQLException.class).when(mockStatement).setLong(1, mockParam);

        assertThrows(DataBaseRuntimeException.class, () -> LONG_CONSUMER.accept(mockStatement, mockParam));
    }

    @Test
    void saveRelationShouldCatchSQLExceptionAndThrowsDataBaseRuntimeException() throws SQLException {

        when(mockDBConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

        doThrow(SQLException.class).when(mockStatement).executeUpdate();

        assertThrows(DataBaseRuntimeException.class, () -> mockCourseDao.saveRelation(StudentCourseRelation.builder().build()));
    }

    @Test
    void findCoursesByStudentIDShouldCatchSQLExceptionAndThrowsDataBaseRuntimeException() throws SQLException {

        when(mockDBConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true).thenThrow(SQLException.class);

        assertThrows(DataBaseRuntimeException.class, () -> mockCourseDao.findCoursesByStudentID(1L));
    }

    @Test
    void findStudentsByCourseIDShouldCatchSQLExceptionAndThrowsDataBaseRuntimeException() throws SQLException {

        when(mockDBConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true).thenThrow(SQLException.class);

        assertThrows(DataBaseRuntimeException.class, () -> mockCourseDao.findStudentsByCourseID(1));
    }

    @Test
    void deleteRelationByStudentIDShouldCatchSQLExceptionAndThrowsDataBaseRuntimeException() throws SQLException {

        when(mockDBConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

        doThrow(SQLException.class).when(mockStatement).execute();

        assertThrows(DataBaseRuntimeException.class, () -> mockCourseDao.deleteRelationByStudentID(1L, 1L));
    }

    @Test
    void deleteAllRelationsByStudentIDShouldCatchSQLExceptionAndThrowsDataBaseRuntimeException() throws SQLException {

        when(mockDBConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

        doThrow(SQLException.class).when(mockStatement).execute();

        System.out.println("Mock statement: " + mockStatement);

        assertThrows(DataBaseRuntimeException.class, () -> mockCourseDao.deleteAllRelationsByStudentID(1L));
    }

    @Test
    void deleteAllRelationsByCourseIDShouldThrowsDataBaseRuntimeException() throws SQLException {

        when(mockDBConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

        doThrow(SQLException.class).when(mockStatement).execute();

        assertThrows(DataBaseRuntimeException.class, () -> mockCourseDao.deleteAllRelationsByCourseID(1L));
    }
}
