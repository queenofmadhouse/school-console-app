package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.DBConnector;
import com.foxminded.chendev.schoolconsoleapp.entity.Course;
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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class CourseDaoImplTestIT {

    private StudentDaoImpl studentDao;
    private StudentCourseRelationDaoImpl studentCourseRelationDao;
    private CourseDaoImpl courseDao;
    private Connection connection;
    private DBConnector connector;


    @BeforeEach
    public void setUp() throws SQLException, IOException {
        connector = new DBConnector("application-test");

        connection = connector.getConnection();

        FileReader fileReader = new SQLFileReader();

        PreparedStatement preparedStatement = connection.prepareStatement(fileReader.readFile("courses_create.sql"));
        preparedStatement.execute();

        preparedStatement = connection.prepareStatement(fileReader.readFile("students_create.sql"));
        preparedStatement.execute();

        preparedStatement = connection.prepareStatement(fileReader.readFile("students_courses_relation.sql"));
        preparedStatement.execute();

        courseDao = new CourseDaoImpl(connector);
        studentDao = new StudentDaoImpl(connector);
        studentCourseRelationDao = new StudentCourseRelationDaoImpl(connector);
    }

    @AfterEach
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    void findCourseByCourseNameShouldReturnCourse() {

        Course course1 = Course.builder()
                .withCourseName("Math")
                .withCourseDescription("Hard level")
                .build();

        Course course2 = Course.builder()
                .withCourseName("Biology")
                .withCourseDescription("Middle level")
                .build();

        Course course3 = Course.builder()
                .withCourseName("Java")
                .withCourseDescription("Super hard level")
                .build();

        courseDao.save(course1);
        courseDao.save(course2);
        courseDao.save(course3);

        Course course = courseDao.findCourseByCourseName("Java");

        assertEquals(3, course.getCourseID());
        assertEquals("Java", course.getCourseName());
        assertEquals("Super hard level", course.getCourseDescription());
    }

    @Test
    void addStudentToCourseShouldCreateRelationBetweenCourseAndStudent() {

        StudentCourseRelation expected = StudentCourseRelation.builder()
                .withStudentID(0)
                .withCourseID(5)
                .build();

        Group group = Group.builder()
                .withGroupID(35)
                .build();

        Student student = Student.builder()
                .withStudentID(1)
                .withFirstName("Alex")
                .withLastName("Smith")
                .withGroupID(group)
                .build();

        courseDao.addStudentToCourse(student, 5);

        List<StudentCourseRelation> studentCourseRelationList = studentCourseRelationDao.findStudentsByCourseID(5);

        assertFalse(studentCourseRelationList.isEmpty());
        assertEquals(1, studentCourseRelationList.size());
        assertEquals(1, studentCourseRelationList.get(0).getStudentID());
        assertEquals(5, studentCourseRelationList.get(0).getCourseID());
    }

    @Test
    void removeStudentFromCourseShouldDeleteStudentFromCourse() {

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

        courseDao.removeStudentFromCourse(3, 35);

        List<StudentCourseRelation> studentCourseRelationList = studentCourseRelationDao.findCoursesByStudentID(3);

        assertFalse(studentCourseRelationList.isEmpty());
        assertEquals(1, studentCourseRelationList.size());
        assertEquals(3, studentCourseRelationList.get(0).getStudentID());
        assertEquals(39, studentCourseRelationList.get(0).getCourseID());
    }

    @Test
    void findAllStudentsByCourseNameShouldReturnListOfStudentsRelatedToCourse() {

        Group group = Group.builder()
                .withGroupID(5)
                .build();

        Student student1 = Student.builder()
                .withFirstName("Joan")
                .withLastName("Roberts")
                .withGroupID(group)
                .build();

        Student student2 = Student.builder()
                .withFirstName("Fillip")
                .withLastName("Some")
                .withGroupID(group)
                .build();
        Student student3 = Student.builder()
                .withFirstName("Jane")
                .withLastName("Potters")
                .withGroupID(group)
                .build();

        Course course = Course.builder()
                .withCourseName("Math")
                .withCourseDescription("Something hard")
                .build();

        courseDao.save(course);

        studentDao.save(student1);
        studentDao.save(student2);
        studentDao.save(student3);

        student1.setStudentID(1);
        student2.setStudentID(2);
        student3.setStudentID(3);

        courseDao.addStudentToCourse(student1, 1);
        courseDao.addStudentToCourse(student2, 2);
        courseDao.addStudentToCourse(student3, 3);

        studentDao.save(student1);
        studentDao.save(student2);
        studentDao.save(student3);

        List<Student> studentList = courseDao.findAllStudentsByCourseName("Math");

        assertEquals(student1.getStudentID(), studentList.get(0).getStudentID());
        assertEquals(student1.getFirstName(), studentList.get(0).getFirstName());
        assertEquals(student1.getLastName(), studentList.get(0).getLastName());
        assertEquals(student1.getGroupID(), studentList.get(0).getGroupID());
    }

    @Test
    void updateShouldUpdateValues() {

        Course course = Course.builder()
                .withCourseName("New Java Course")
                .withCourseDescription("With old information")
                .build();

        courseDao.save(course);

        Course courseFounded = courseDao.findCourseByCourseName("New Java Course");
        courseFounded.setCourseName("Old Java Course");
        courseFounded.setCourseDescription("Without info at all");

        courseDao.update(courseFounded);

        Course courseUpdated = courseDao.findById(1).orElse(null);

        assertNotNull(courseUpdated);
        assertEquals("Old Java Course", courseUpdated.getCourseName());
        assertEquals("Without info at all", courseUpdated.getCourseDescription());
    }

    @Test
    void deleteByIDShouldDeleteAllRelations() {

        Group group = Group.builder()
                .withGroupID(5)
                .build();

        Student student1 = Student.builder()
                .withFirstName("Joan")
                .withLastName("Roberts")
                .withGroupID(group)
                .build();

        Student student2 = Student.builder()
                .withFirstName("Fillip")
                .withLastName("Some")
                .withGroupID(group)
                .build();
        Student student3 = Student.builder()
                .withFirstName("Jane")
                .withLastName("Potters")
                .withGroupID(group)
                .build();

        Course course1 = Course.builder()
                .withCourseName("Math")
                .withCourseDescription("Something hard")
                .build();
        Course course2 = Course.builder()
                .withCourseName("Biology")
                .withCourseDescription("Something hard")
                .build();
        Course course3 = Course.builder()
                .withCourseName("Art")
                .withCourseDescription("Something hard")
                .build();

        courseDao.save(course1);
        courseDao.save(course2);
        courseDao.save(course3);

        studentDao.save(student1);
        studentDao.save(student2);
        studentDao.save(student3);

        student1.setStudentID(1);
        student2.setStudentID(2);
        student3.setStudentID(3);

        courseDao.addStudentToCourse(student1, 1);
        courseDao.addStudentToCourse(student2, 2);
        courseDao.addStudentToCourse(student3, 3);

        studentDao.save(student1);
        studentDao.save(student2);
        studentDao.save(student3);

        courseDao.deleteByID(1);

        List<StudentCourseRelation> studentCourseRelationFound = studentCourseRelationDao.findStudentsByCourseID(1);
        Course course = courseDao.findById(1).orElse(null);

        assertTrue(studentCourseRelationFound.isEmpty());
        assertNull(course);
    }

    @Test
    void findAllShouldFindAllCoursesAndReturnList() {

        Course course1 = Course.builder()
                .withCourseName("Math")
                .withCourseDescription("Something hard")
                .build();
        Course course2 = Course.builder()
                .withCourseName("Biology")
                .withCourseDescription("Something hard")
                .build();
        Course course3 = Course.builder()
                .withCourseName("Art")
                .withCourseDescription("Something hard")
                .build();

        courseDao.save(course1);
        courseDao.save(course2);
        courseDao.save(course3);

        List<Course> courseList = courseDao.findAll();

        assertNotNull(courseList);
        assertEquals(3, courseList.size());
        assertEquals("Math", courseList.get(0).getCourseName());
        assertEquals("Something hard", courseList.get(0).getCourseDescription());
        assertEquals("Biology", courseList.get(1).getCourseName());
        assertEquals("Something hard", courseList.get(1).getCourseDescription());
        assertEquals("Art", courseList.get(2).getCourseName());
        assertEquals("Something hard", courseList.get(2).getCourseDescription());
    }
}
