package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.DBConnector;
import com.foxminded.chendev.schoolconsoleapp.entity.StudentCourseRelation;
import com.foxminded.chendev.schoolconsoleapp.reader.FileReader;
import com.foxminded.chendev.schoolconsoleapp.reader.SQLFileReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentCourseRelationDaoImplTestIT {
    private StudentCourseRelationDaoImpl studentCourseRelationDao;
    private Connection connection;
    private DBConnector connector;

    @BeforeEach
    public void setUp() throws SQLException, IOException {
        connector = new DBConnector("application-test");

        connection = connector.getConnection();

        FileReader fileReader = new SQLFileReader();

        PreparedStatement preparedStatement = connection.prepareStatement(fileReader.readFile("courses_create.sql"));
        preparedStatement.execute();

        preparedStatement = connection.prepareStatement(fileReader.readFile("students_courses_relation.sql"));
        preparedStatement.execute();

        studentCourseRelationDao = new StudentCourseRelationDaoImpl(connector);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    void findCoursesByStudentIDShouldReturnListOfStudentCourseRelationEntity() {

        StudentCourseRelation studentCourseRelation1 = StudentCourseRelation.builder()
                .withStudentID(1)
                .withCourseID(30)
                .build();
        StudentCourseRelation studentCourseRelation2 = StudentCourseRelation.builder()
                .withStudentID(1)
                .withCourseID(35)
                .build();
        StudentCourseRelation studentCourseRelation3 = StudentCourseRelation.builder()
                .withStudentID(1)
                .withCourseID(35)
                .build();
        StudentCourseRelation studentCourseRelation4 = StudentCourseRelation.builder()
                .withStudentID(2)
                .withCourseID(20)
                .build();
        StudentCourseRelation studentCourseRelation5 = StudentCourseRelation.builder()
                .withStudentID(2)
                .withCourseID(9)
                .build();

        studentCourseRelationDao.saveRelation(studentCourseRelation1);
        studentCourseRelationDao.saveRelation(studentCourseRelation2);
        studentCourseRelationDao.saveRelation(studentCourseRelation3);
        studentCourseRelationDao.saveRelation(studentCourseRelation4);
        studentCourseRelationDao.saveRelation(studentCourseRelation5);

        List<StudentCourseRelation> resultList = studentCourseRelationDao.findCoursesByStudentID(1);

        assertFalse(resultList.isEmpty());
        assertEquals(3, resultList.size());
        assertEquals(1, resultList.get(0).getStudentID());
        assertEquals(1, resultList.get(1).getStudentID());
        assertEquals(1, resultList.get(2).getStudentID());
    }

    @Test
    void findStudentsByCourseIDShouldReturnListOfStudentCourseRelationEntity() {

        StudentCourseRelation studentCourseRelation1 = StudentCourseRelation.builder()
                .withStudentID(1)
                .withCourseID(35)
                .build();
        StudentCourseRelation studentCourseRelation2 = StudentCourseRelation.builder()
                .withStudentID(2)
                .withCourseID(35)
                .build();
        StudentCourseRelation studentCourseRelation3 = StudentCourseRelation.builder()
                .withStudentID(3)
                .withCourseID(35)
                .build();
        StudentCourseRelation studentCourseRelation4 = StudentCourseRelation.builder()
                .withStudentID(4)
                .withCourseID(81)
                .build();
        StudentCourseRelation studentCourseRelation5 = StudentCourseRelation.builder()
                .withStudentID(2)
                .withCourseID(9)
                .build();

        studentCourseRelationDao.saveRelation(studentCourseRelation1);
        studentCourseRelationDao.saveRelation(studentCourseRelation2);
        studentCourseRelationDao.saveRelation(studentCourseRelation3);
        studentCourseRelationDao.saveRelation(studentCourseRelation4);
        studentCourseRelationDao.saveRelation(studentCourseRelation5);

        List<StudentCourseRelation> resultList = studentCourseRelationDao.findStudentsByCourseID(35);

        assertFalse(resultList.isEmpty());
        assertEquals(3, resultList.size());
        assertEquals(35, resultList.get(0).getCourseID());
        assertEquals(35, resultList.get(1).getCourseID());
        assertEquals(35, resultList.get(2).getCourseID());
        assertEquals(1, resultList.get(0).getStudentID());
        assertEquals(2, resultList.get(1).getStudentID());
        assertEquals(3, resultList.get(2).getStudentID());
    }

    @Test
    void deleteRelationByStudentIDShouldDeleteOneRelation() {

        StudentCourseRelation studentCourseRelation1 = StudentCourseRelation.builder()
                .withStudentID(1)
                .withCourseID(35)
                .build();
        StudentCourseRelation studentCourseRelation2 = StudentCourseRelation.builder()
                .withStudentID(3)
                .withCourseID(35)
                .build();
        StudentCourseRelation studentCourseRelation3 = StudentCourseRelation.builder()
                .withStudentID(3)
                .withCourseID(39)
                .build();
        StudentCourseRelation studentCourseRelation4 = StudentCourseRelation.builder()
                .withStudentID(4)
                .withCourseID(81)
                .build();
        StudentCourseRelation studentCourseRelation5 = StudentCourseRelation.builder()
                .withStudentID(2)
                .withCourseID(9)
                .build();

        studentCourseRelationDao.saveRelation(studentCourseRelation1);
        studentCourseRelationDao.saveRelation(studentCourseRelation2);
        studentCourseRelationDao.saveRelation(studentCourseRelation3);
        studentCourseRelationDao.saveRelation(studentCourseRelation4);
        studentCourseRelationDao.saveRelation(studentCourseRelation5);

        studentCourseRelationDao.deleteRelationByStudentID(3, 35);

        List<StudentCourseRelation> studentCourseRelationList = studentCourseRelationDao.findCoursesByStudentID(3);

        assertFalse(studentCourseRelationList.isEmpty());
        assertEquals(1, studentCourseRelationList.size());
        assertEquals(3, studentCourseRelationList.get(0).getStudentID());
        assertEquals(39, studentCourseRelationList.get(0).getCourseID());

    }

    @Test
    void deleteAllRelationsByStudentIDShouldDeleteAllRelationsRelatedToStudentID() {

        StudentCourseRelation studentCourseRelation1 = StudentCourseRelation.builder()
                .withStudentID(1)
                .withCourseID(35)
                .build();
        StudentCourseRelation studentCourseRelation2 = StudentCourseRelation.builder()
                .withStudentID(3)
                .withCourseID(35)
                .build();
        StudentCourseRelation studentCourseRelation3 = StudentCourseRelation.builder()
                .withStudentID(3)
                .withCourseID(39)
                .build();
        StudentCourseRelation studentCourseRelation4 = StudentCourseRelation.builder()
                .withStudentID(4)
                .withCourseID(81)
                .build();
        StudentCourseRelation studentCourseRelation5 = StudentCourseRelation.builder()
                .withStudentID(2)
                .withCourseID(9)
                .build();

        studentCourseRelationDao.saveRelation(studentCourseRelation1);
        studentCourseRelationDao.saveRelation(studentCourseRelation2);
        studentCourseRelationDao.saveRelation(studentCourseRelation3);
        studentCourseRelationDao.saveRelation(studentCourseRelation4);
        studentCourseRelationDao.saveRelation(studentCourseRelation5);

        studentCourseRelationDao.deleteAllRelationsByStudentID(3);

        List<StudentCourseRelation> studentCourseRelationList = studentCourseRelationDao.findCoursesByStudentID(3);

        assertTrue(studentCourseRelationList.isEmpty());
    }

    @Test
    void deleteAllRelationsByCourseIDShouldDeleteAllRelationsRelatedToCourseID() {

        StudentCourseRelation studentCourseRelation1 = StudentCourseRelation.builder()
                .withStudentID(1)
                .withCourseID(35)
                .build();
        StudentCourseRelation studentCourseRelation2 = StudentCourseRelation.builder()
                .withStudentID(3)
                .withCourseID(35)
                .build();
        StudentCourseRelation studentCourseRelation3 = StudentCourseRelation.builder()
                .withStudentID(3)
                .withCourseID(39)
                .build();
        StudentCourseRelation studentCourseRelation4 = StudentCourseRelation.builder()
                .withStudentID(4)
                .withCourseID(81)
                .build();
        StudentCourseRelation studentCourseRelation5 = StudentCourseRelation.builder()
                .withStudentID(2)
                .withCourseID(9)
                .build();

        studentCourseRelationDao.saveRelation(studentCourseRelation1);
        studentCourseRelationDao.saveRelation(studentCourseRelation2);
        studentCourseRelationDao.saveRelation(studentCourseRelation3);
        studentCourseRelationDao.saveRelation(studentCourseRelation4);
        studentCourseRelationDao.saveRelation(studentCourseRelation5);

        studentCourseRelationDao.deleteAllRelationsByCourseID(35);

        List<StudentCourseRelation> studentCourseRelationList = studentCourseRelationDao.findStudentsByCourseID(35);

        assertTrue(studentCourseRelationList.isEmpty());
    }
}
