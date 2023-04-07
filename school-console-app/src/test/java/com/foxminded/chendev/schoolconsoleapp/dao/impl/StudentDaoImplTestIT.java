package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.DBConnector;
import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StudentDaoImplTestIT {

    private StudentDaoImpl studentDao;
    private CourseDaoImpl courseDao;
    private Connection connection;
    private DBConnector connector;

    private final String sqlQuery = "SELECT * FROM school.students WHERE first_name = ?";
    private final FileReader fileReader = new SQLFileReader();

    @BeforeEach
    public void setUp() throws SQLException, IOException {

        connector = new DBConnector("application-test");

        connection = connector.getConnection();

        PreparedStatement preparedStatement = connection.prepareStatement(fileReader.readFile("students_create.sql"));
        preparedStatement.execute();

        preparedStatement = connection.prepareStatement(fileReader.readFile("students_courses_relation.sql"));
        preparedStatement.execute();

        studentDao = new StudentDaoImpl(connector);
        courseDao = new CourseDaoImpl(connector);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    void saveShouldSaveInEntityInDataBase() {

        Group group = Group.builder()
                .withGroupId(1)
                .build();

        Student student = Student.builder()
                .withFirstName("Alex")
                .withLastName("Kapranos")
                .withGroupId(group)
                .build();

        studentDao.save(student);

        Student findedStudent = studentDao.findById(1).orElse(null);

        assertEquals("Alex", findedStudent.getFirstName());
        assertEquals("Kapranos", findedStudent.getLastName());
        assertEquals(1, findedStudent.getStudentId());

    }

    @Test
    void findAllShouldReturnListOfStudentWhenFound() {

        Group group = Group.builder()
                .withGroupId(1)
                .build();

        Student student1 = Student.builder()
                .withFirstName("Alex")
                .withLastName("Kapranos")
                .withGroupId(group)
                .build();
        Student student2 = Student.builder()
                .withFirstName("Nikol")
                .withLastName("Smith")
                .withGroupId(group)
                .build();

        studentDao.save(student1);
        studentDao.save(student2);

        List<Student> resultList = studentDao.findAll();

        assertFalse(resultList.isEmpty());
        assertEquals(2, resultList.size());
        ;
    }

    @Test
    void updateShouldUpdateValues() {

        Group group = Group.builder()
                .withGroupId(1)
                .build();

        Student student = Student.builder()
                .withFirstName("Alex")
                .withLastName("Kapranos")
                .withGroupId(group)
                .build();

        studentDao.save(student);

        Student foundStudent = studentDao.findById(1).orElse(null);

        foundStudent.setFirstName("Alexandr");
        foundStudent.setLastName("Kirieshkin");

        studentDao.update(foundStudent);

        Student updatedStudent = studentDao.findById(1).orElse(null);

        assertNotNull(updatedStudent);
        assertEquals("Alexandr", updatedStudent.getFirstName());
        assertEquals("Kirieshkin", updatedStudent.getLastName());

    }

    @Test
    void deleteByIDShouldDeleteAllRelations() {

        Group group = Group.builder()
                .withGroupId(1)
                .build();

        StudentCourseRelation studentCourseRelation1 = StudentCourseRelation.builder()
                .withStudentId(1)
                .withCourseId(30)
                .build();
        StudentCourseRelation studentCourseRelation2 = StudentCourseRelation.builder()
                .withStudentId(1)
                .withCourseId(35)
                .build();
        StudentCourseRelation studentCourseRelation3 = StudentCourseRelation.builder()
                .withStudentId(1)
                .withCourseId(38)
                .build();
        StudentCourseRelation studentCourseRelation4 = StudentCourseRelation.builder()
                .withStudentId(2)
                .withCourseId(20)
                .build();
        StudentCourseRelation studentCourseRelation5 = StudentCourseRelation.builder()
                .withStudentId(2)
                .withCourseId(9)
                .build();

        Student student = Student.builder()
                .withFirstName("Alex")
                .withLastName("Smith")
                .withGroupId(group)
                .build();

        courseDao.saveRelation(studentCourseRelation1);
        courseDao.saveRelation(studentCourseRelation2);
        courseDao.saveRelation(studentCourseRelation3);
        courseDao.saveRelation(studentCourseRelation4);
        courseDao.saveRelation(studentCourseRelation5);

        studentDao.save(student);

        studentDao.deleteByID(1);

        List<StudentCourseRelation> resultList = courseDao.findCoursesByStudentID(1);

        assertTrue(resultList.isEmpty());
    }

    @Test
    void findByStringParamShouldFindByStringParam() {

        Student student = Student.builder()
                .withFirstName("Vasil")
                .withLastName("Summer")
                .build();
        studentDao.save(student);

        Student studentFound = studentDao.findByStringParam("Vasil", sqlQuery).orElse(null);

        assertNotNull(studentFound);
        assertEquals("Vasil", studentFound.getFirstName());
        assertEquals("Summer", studentFound.getLastName());
    }

    @Test
    void findByStringParamShouldThrowExceptionWhenNotFound() {

        Student student = Student.builder()
                .withFirstName("Vasil")
                .withLastName("Summer")
                .build();

        studentDao.save(student);

        Student studentFound = studentDao.findByStringParam("Vasilisa", sqlQuery).orElse(null);

        assertNull(studentFound);

    }
}
