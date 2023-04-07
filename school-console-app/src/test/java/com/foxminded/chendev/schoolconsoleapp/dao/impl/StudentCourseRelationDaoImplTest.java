package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.DBConnector;
import com.foxminded.chendev.schoolconsoleapp.dao.DataBaseRuntimeException;
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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;

@ExtendWith(MockitoExtension.class)
public class StudentCourseRelationDaoImplTest {

    @Mock
    private DBConnector mockDBConnector;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private StudentCourseRelationDaoImpl mockStudentCourseRelationDao;

    @Test
    public void saveRelationShouldCatchSQLExceptionAndThrowsDataBaseRuntimeException() throws SQLException {

        when(mockDBConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

        doThrow(SQLException.class).when(mockStatement).executeUpdate();

        assertThrows(DataBaseRuntimeException.class, () -> mockStudentCourseRelationDao.saveRelation(StudentCourseRelation.builder().build()));
    }

    @Test
    public void findCoursesByStudentIDShouldCatchSQLExceptionAndThrowsDataBaseRuntimeException() throws SQLException {

        when(mockDBConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true).thenThrow(SQLException.class);

        assertThrows(DataBaseRuntimeException.class, () -> mockStudentCourseRelationDao.findCoursesByStudentID(1L));
    }

    @Test
    public void findStudentsByCourseIDShouldCatchSQLExceptionAndThrowsDataBaseRuntimeException() throws SQLException {

        when(mockDBConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);

        when(mockResultSet.next()).thenReturn(true).thenThrow(SQLException.class);

        assertThrows(DataBaseRuntimeException.class, () -> mockStudentCourseRelationDao.findStudentsByCourseID(1));
    }

    @Test
    public void deleteRelationByStudentIDShouldCatchSQLExceptionAndThrowsDataBaseRuntimeException() throws SQLException {

        when(mockDBConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

        doThrow(SQLException.class).when(mockStatement).execute();

        assertThrows(DataBaseRuntimeException.class, () -> mockStudentCourseRelationDao.deleteRelationByStudentID(1L, 1L));
    }

    @Test
    public void deleteAllRelationsByStudentIDShouldCatchSQLExceptionAndThrowsDataBaseRuntimeException() throws SQLException {

        when(mockDBConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

        doThrow(SQLException.class).when(mockStatement).execute();

        System.out.println("Mock statement: " + mockStatement);

        assertThrows(DataBaseRuntimeException.class, () -> mockStudentCourseRelationDao.deleteAllRelationsByStudentID(1L));
    }

    @Test
    public void deleteAllRelationsByCourseIDShouldThrowsDataBaseRuntimeException() throws SQLException {

        when(mockDBConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);

        doThrow(SQLException.class).when(mockStatement).execute();

        assertThrows(DataBaseRuntimeException.class, () -> mockStudentCourseRelationDao.deleteAllRelationsByCourseID(1L));
    }
}

