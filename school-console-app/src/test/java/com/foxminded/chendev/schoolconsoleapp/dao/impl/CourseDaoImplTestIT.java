package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.entity.StudentCourseRelation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"/sql/clear_tables.sql",
                "/sql/students_create.sql",
                "/sql/groups_create.sql",
                "/sql/courses_create.sql",
                "/sql/students_courses_relation.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class CourseDaoImplTestIT {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CourseDaoImpl courseDao;

    private StudentDaoImpl studentDao;

    @BeforeEach
    void setUp() {
        courseDao = new CourseDaoImpl(jdbcTemplate);
        studentDao = new StudentDaoImpl(jdbcTemplate);
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

        assertEquals(3, course.getCourseId());
        assertEquals("Java", course.getCourseName());
        assertEquals("Super hard level", course.getCourseDescription());
    }

    @Test
    void addStudentToCourseShouldCreateRelationBetweenCourseAndStudent() {

        Student student = Student.builder()
                .withStudentId(1)
                .withFirstName("Alex")
                .withLastName("Smith")
                .withGroupId(35)
                .build();

        courseDao.addStudentToCourse(student, 5);

        List<StudentCourseRelation> studentCourseRelationList = courseDao.findStudentsByCourseID(5);

        assertFalse(studentCourseRelationList.isEmpty());
        assertEquals(1, studentCourseRelationList.size());
        assertEquals(1, studentCourseRelationList.get(0).getStudentId());
        assertEquals(5, studentCourseRelationList.get(0).getCourseId());
    }

    @Test
    void removeStudentFromCourseShouldDeleteStudentFromCourse() {

        StudentCourseRelation studentCourseRelation1 = StudentCourseRelation.builder()
                .withStudentId(1)
                .withCourseId(35)
                .build();
        StudentCourseRelation studentCourseRelation2 = StudentCourseRelation.builder()
                .withStudentId(3)
                .withCourseId(35)
                .build();
        StudentCourseRelation studentCourseRelation3 = StudentCourseRelation.builder()
                .withStudentId(3)
                .withCourseId(39)
                .build();
        StudentCourseRelation studentCourseRelation4 = StudentCourseRelation.builder()
                .withStudentId(4)
                .withCourseId(81)
                .build();
        StudentCourseRelation studentCourseRelation5 = StudentCourseRelation.builder()
                .withStudentId(2)
                .withCourseId(9)
                .build();

        courseDao.saveRelation(studentCourseRelation1);
        courseDao.saveRelation(studentCourseRelation2);
        courseDao.saveRelation(studentCourseRelation3);
        courseDao.saveRelation(studentCourseRelation4);
        courseDao.saveRelation(studentCourseRelation5);

        courseDao.removeStudentFromCourse(3, 35);

        List<StudentCourseRelation> studentCourseRelationList = courseDao.findCoursesByStudentID(3);

        assertFalse(studentCourseRelationList.isEmpty());
        assertEquals(1, studentCourseRelationList.size());
        assertEquals(3, studentCourseRelationList.get(0).getStudentId());
        assertEquals(39, studentCourseRelationList.get(0).getCourseId());
    }

    @Test
    void findAllStudentsByCourseNameShouldReturnListOfStudentsRelatedToCourse() {

        Student student1 = Student.builder()
                .withFirstName("Joan")
                .withLastName("Roberts")
                .withGroupId(5)
                .build();

        Student student2 = Student.builder()
                .withFirstName("Fillip")
                .withLastName("Some")
                .withGroupId(5)
                .build();
        Student student3 = Student.builder()
                .withFirstName("Jane")
                .withLastName("Potters")
                .withGroupId(5)
                .build();

        Course course = Course.builder()
                .withCourseName("Math")
                .withCourseDescription("Something hard")
                .build();

        courseDao.save(course);

        studentDao.save(student1);
        studentDao.save(student2);
        studentDao.save(student3);

        student1.setStudentId(1);
        student2.setStudentId(2);
        student3.setStudentId(3);

        courseDao.addStudentToCourse(student1, 1);
        courseDao.addStudentToCourse(student2, 2);
        courseDao.addStudentToCourse(student3, 3);

        studentDao.save(student1);
        studentDao.save(student2);
        studentDao.save(student3);

        List<Student> studentList = courseDao.findAllStudentsByCourseName("Math");

        assertEquals(student1.getStudentId(), studentList.get(0).getStudentId());
        assertEquals(student1.getFirstName(), studentList.get(0).getFirstName());
        assertEquals(student1.getLastName(), studentList.get(0).getLastName());
        assertEquals(student1.getGroupId(), studentList.get(0).getGroupId());
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

        Course courseUpdated = courseDao.findById(1);

        assertNotNull(courseUpdated);
        assertEquals("Old Java Course", courseUpdated.getCourseName());
        assertEquals("Without info at all", courseUpdated.getCourseDescription());
    }

    @Test
    void deleteByIDShouldDeleteAllRelations() {

        Group group = Group.builder()
                .withGroupId(5)
                .build();

        Student student1 = Student.builder()
                .withFirstName("Joan")
                .withLastName("Roberts")
                .withGroupId(5)
                .build();

        Student student2 = Student.builder()
                .withFirstName("Fillip")
                .withLastName("Some")
                .withGroupId(5)
                .build();
        Student student3 = Student.builder()
                .withFirstName("Jane")
                .withLastName("Potters")
                .withGroupId(5)
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

        student1.setStudentId(1);
        student2.setStudentId(2);
        student3.setStudentId(3);

        courseDao.addStudentToCourse(student1, 1);
        courseDao.addStudentToCourse(student2, 2);
        courseDao.addStudentToCourse(student3, 3);

        studentDao.save(student1);
        studentDao.save(student2);
        studentDao.save(student3);

        courseDao.deleteByID(1);

        List<StudentCourseRelation> studentCourseRelationFound = courseDao.findStudentsByCourseID(1);

        System.out.println(studentCourseRelationFound);

        assertTrue(studentCourseRelationFound.isEmpty());
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

    @Test
    void findCoursesByStudentIDShouldReturnListOfStudentCourseRelationEntity() {

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
                .withCourseId(35)
                .build();
        StudentCourseRelation studentCourseRelation4 = StudentCourseRelation.builder()
                .withStudentId(2)
                .withCourseId(20)
                .build();
        StudentCourseRelation studentCourseRelation5 = StudentCourseRelation.builder()
                .withStudentId(2)
                .withCourseId(9)
                .build();

        courseDao.saveRelation(studentCourseRelation1);
        courseDao.saveRelation(studentCourseRelation2);
        courseDao.saveRelation(studentCourseRelation3);
        courseDao.saveRelation(studentCourseRelation4);
        courseDao.saveRelation(studentCourseRelation5);

        List<StudentCourseRelation> resultList = courseDao.findCoursesByStudentID(1);

        assertFalse(resultList.isEmpty());
        assertEquals(3, resultList.size());
        assertEquals(1, resultList.get(0).getStudentId());
        assertEquals(1, resultList.get(1).getStudentId());
        assertEquals(1, resultList.get(2).getStudentId());
    }

    @Test
    void findStudentsByCourseIDShouldReturnListOfStudentCourseRelationEntity() {

        StudentCourseRelation studentCourseRelation1 = StudentCourseRelation.builder()
                .withStudentId(1)
                .withCourseId(35)
                .build();
        StudentCourseRelation studentCourseRelation2 = StudentCourseRelation.builder()
                .withStudentId(2)
                .withCourseId(35)
                .build();
        StudentCourseRelation studentCourseRelation3 = StudentCourseRelation.builder()
                .withStudentId(3)
                .withCourseId(35)
                .build();
        StudentCourseRelation studentCourseRelation4 = StudentCourseRelation.builder()
                .withStudentId(4)
                .withCourseId(81)
                .build();
        StudentCourseRelation studentCourseRelation5 = StudentCourseRelation.builder()
                .withStudentId(2)
                .withCourseId(9)
                .build();

        courseDao.saveRelation(studentCourseRelation1);
        courseDao.saveRelation(studentCourseRelation2);
        courseDao.saveRelation(studentCourseRelation3);
        courseDao.saveRelation(studentCourseRelation4);
        courseDao.saveRelation(studentCourseRelation5);

        List<StudentCourseRelation> resultList = courseDao.findStudentsByCourseID(35);

        assertFalse(resultList.isEmpty());
        assertEquals(3, resultList.size());
        assertEquals(35, resultList.get(0).getCourseId());
        assertEquals(35, resultList.get(1).getCourseId());
        assertEquals(35, resultList.get(2).getCourseId());
        assertEquals(1, resultList.get(0).getStudentId());
        assertEquals(2, resultList.get(1).getStudentId());
        assertEquals(3, resultList.get(2).getStudentId());
    }

    @Test
    void deleteRelationByStudentIDShouldDeleteOneRelation() {

        StudentCourseRelation studentCourseRelation1 = StudentCourseRelation.builder()
                .withStudentId(1)
                .withCourseId(35)
                .build();
        StudentCourseRelation studentCourseRelation2 = StudentCourseRelation.builder()
                .withStudentId(3)
                .withCourseId(35)
                .build();
        StudentCourseRelation studentCourseRelation3 = StudentCourseRelation.builder()
                .withStudentId(3)
                .withCourseId(39)
                .build();
        StudentCourseRelation studentCourseRelation4 = StudentCourseRelation.builder()
                .withStudentId(4)
                .withCourseId(81)
                .build();
        StudentCourseRelation studentCourseRelation5 = StudentCourseRelation.builder()
                .withStudentId(2)
                .withCourseId(9)
                .build();

        courseDao.saveRelation(studentCourseRelation1);
        courseDao.saveRelation(studentCourseRelation2);
        courseDao.saveRelation(studentCourseRelation3);
        courseDao.saveRelation(studentCourseRelation4);
        courseDao.saveRelation(studentCourseRelation5);

        courseDao.deleteRelationByStudentID(3, 35);

        List<StudentCourseRelation> studentCourseRelationList = courseDao.findCoursesByStudentID(3);

        assertFalse(studentCourseRelationList.isEmpty());
        assertEquals(1, studentCourseRelationList.size());
        assertEquals(3, studentCourseRelationList.get(0).getStudentId());
        assertEquals(39, studentCourseRelationList.get(0).getCourseId());

    }

    @Test
    void deleteAllRelationsByStudentIDShouldDeleteAllRelationsRelatedToStudentID() {

        StudentCourseRelation studentCourseRelation1 = StudentCourseRelation.builder()
                .withStudentId(1)
                .withCourseId(35)
                .build();
        StudentCourseRelation studentCourseRelation2 = StudentCourseRelation.builder()
                .withStudentId(3)
                .withCourseId(35)
                .build();
        StudentCourseRelation studentCourseRelation3 = StudentCourseRelation.builder()
                .withStudentId(3)
                .withCourseId(39)
                .build();
        StudentCourseRelation studentCourseRelation4 = StudentCourseRelation.builder()
                .withStudentId(4)
                .withCourseId(81)
                .build();
        StudentCourseRelation studentCourseRelation5 = StudentCourseRelation.builder()
                .withStudentId(2)
                .withCourseId(9)
                .build();

        courseDao.saveRelation(studentCourseRelation1);
        courseDao.saveRelation(studentCourseRelation2);
        courseDao.saveRelation(studentCourseRelation3);
        courseDao.saveRelation(studentCourseRelation4);
        courseDao.saveRelation(studentCourseRelation5);

        courseDao.deleteAllRelationsByStudentID(3);

        List<StudentCourseRelation> studentCourseRelationList = courseDao.findCoursesByStudentID(3);

        assertTrue(studentCourseRelationList.isEmpty());
    }

    @Test
    void deleteAllRelationsByCourseIDShouldDeleteAllRelationsRelatedToCourseID() {

        StudentCourseRelation studentCourseRelation1 = StudentCourseRelation.builder()
                .withStudentId(1)
                .withCourseId(35)
                .build();
        StudentCourseRelation studentCourseRelation2 = StudentCourseRelation.builder()
                .withStudentId(3)
                .withCourseId(35)
                .build();
        StudentCourseRelation studentCourseRelation3 = StudentCourseRelation.builder()
                .withStudentId(3)
                .withCourseId(39)
                .build();
        StudentCourseRelation studentCourseRelation4 = StudentCourseRelation.builder()
                .withStudentId(4)
                .withCourseId(81)
                .build();
        StudentCourseRelation studentCourseRelation5 = StudentCourseRelation.builder()
                .withStudentId(2)
                .withCourseId(9)
                .build();

        courseDao.saveRelation(studentCourseRelation1);
        courseDao.saveRelation(studentCourseRelation2);
        courseDao.saveRelation(studentCourseRelation3);
        courseDao.saveRelation(studentCourseRelation4);
        courseDao.saveRelation(studentCourseRelation5);

        courseDao.deleteAllRelationsByCourseID(35);

        List<StudentCourseRelation> studentCourseRelationList = courseDao.findStudentsByCourseID(35);

        assertTrue(studentCourseRelationList.isEmpty());
    }
}
