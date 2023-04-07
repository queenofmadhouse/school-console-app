package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.DBConnector;
import com.foxminded.chendev.schoolconsoleapp.dao.DataBaseRuntimeException;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
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
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentDaoImplTest {

    @Mock
    private DBConnector mockConnector;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockStatement;

    @Mock
    private ResultSet mockResultSet;

    @InjectMocks
    private StudentDaoImpl mockStudentDao;

    @Test
    void saveShouldThrowDataBaseRuntimeException() throws SQLException {

        when(mockConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doThrow(SQLException.class).when(mockStatement).execute();

        assertThrows(DataBaseRuntimeException.class, () -> mockStudentDao.save(Student.builder().build()));
    }

    @Test
    void findByIdShouldThrowDataBaseRuntimeException() throws SQLException {

        when(mockConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenThrow(SQLException.class);

        assertThrows(DataBaseRuntimeException.class, () -> mockStudentDao.findById(1L));
    }

    @Test
    void findAllShouldThrowDataBaseRuntimeException() throws SQLException {

        when(mockConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenThrow(SQLException.class);

        assertThrows(DataBaseRuntimeException.class, () -> mockStudentDao.findAll());
    }

    @Test
    void updateShouldThrowDataBaseRuntimeException() throws SQLException {

        when(mockConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doThrow(SQLException.class).when(mockStatement).executeUpdate();

        assertThrows(DataBaseRuntimeException.class, () -> mockStudentDao.update(Student.builder().build()));
    }

    @Test
    void deleteByIdShouldThrowDataBaseRuntimeException() throws SQLException {

        when(mockConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        doThrow(SQLException.class).when(mockStatement).executeUpdate();

        assertThrows(DataBaseRuntimeException.class, () -> mockStudentDao.deleteByID(1L));
    }

    @Test
    void findByStringParamShouldThrowDataBaseRuntimeException() throws SQLException {

        when(mockConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenThrow(SQLException.class);

        assertThrows(DataBaseRuntimeException.class, () -> mockStudentDao.findByStringParam("param", "query"));
    }

    @Test
    void findByStringParamShouldThrowsDataBaseRuntimeException() throws SQLException {

        when(mockConnector.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenThrow(SQLException.class);

        assertThrows(DataBaseRuntimeException.class, () -> mockStudentDao.findByStringParam("test", "SELECT * FROM school.students WHERE first_name = ?"));
    }
}
