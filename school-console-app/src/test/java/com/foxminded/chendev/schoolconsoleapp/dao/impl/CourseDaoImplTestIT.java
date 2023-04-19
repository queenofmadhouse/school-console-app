package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@Sql(
        scripts = {"/sql/clear_tables.sql",
                "/sql/users_create.sql",
                "/sql/students_create.sql",
                "/sql/groups_create.sql",
                "/sql/courses_create.sql",
                "/sql/students_courses_relation.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class CourseDaoImplTestIT {

    @Autowired
    private CourseDaoImpl courseDao;

    @Autowired
    private StudentDaoImpl studentDao;

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

        Course course = courseDao.findCourseByCourseName("Java").orElse(null);

        assertNotNull(course);
        assertEquals(3, course.getCourseId());
        assertEquals("Java", course.getCourseName());
        assertEquals("Super hard level", course.getCourseDescription());
    }

    @Test
    void deleteByIdShouldDelete() {

        Course course = Course.builder()
                .withCourseName("Math")
                .withCourseDescription("Something hard")
                .build();

        courseDao.save(course);

        courseDao.deleteById(1);

        Optional<Course> optionalCourse = courseDao.findById(1);

        assertFalse(optionalCourse.isPresent());
    }

    @Test
    void updateShouldUpdateValues() {

        Course course = Course.builder()
                .withCourseName("New Java Course")
                .withCourseDescription("With old information")
                .build();

        courseDao.save(course);

        Course courseFounded = courseDao.findCourseByCourseName("New Java Course").orElse(null);
        courseFounded.setCourseName("Old Java Course");
        courseFounded.setCourseDescription("Without info at all");

        courseDao.update(courseFounded);

        Course courseUpdated = courseDao.findById(1).orElse(null);

        assertNotNull(courseUpdated);
        assertEquals("Old Java Course", courseUpdated.getCourseName());
        assertEquals("Without info at all", courseUpdated.getCourseDescription());
    }
//
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

        Student student1 = Student.builder()
                .withFirstName("Alex")
                .withLastName("Smith")
                .withGroupId(35)
                .build();

        Student student2 = Student.builder()
                .withFirstName("Boyana")
                .withLastName("Arbams")
                .withGroupId(35)
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

        studentDao.save(student1);
        studentDao.save(student2);

        courseDao.save(course1);
        courseDao.save(course2);
        courseDao.save(course3);

        studentDao.addStudentToCourse(1, 1);
        studentDao.addStudentToCourse(1, 2);
        studentDao.addStudentToCourse(2, 3);

        List<Course> courseList = courseDao.findCoursesByStudentId(1);

        assertFalse(courseList.isEmpty());
        assertEquals(2, courseList.size());
        assertEquals(1, courseList.get(0).getCourseId());
        assertEquals("Math", courseList.get(0).getCourseName());
        assertEquals(2, courseList.get(1).getCourseId());
        assertEquals("Biology", courseList.get(1).getCourseName());
    }

    @Test
    void deleteAllRelationsByCourseIDShouldDeleteAllRelationsRelatedToCourseID() {

        studentDao.addStudentToCourse(5, 8);
        studentDao.addStudentToCourse(3, 8);
        studentDao.addStudentToCourse(10, 8);
        studentDao.addStudentToCourse(200, 8);
        studentDao.addStudentToCourse(1, 8);

        courseDao.deleteAllRelationsByCourseId(8);

        List<Student> studentList = studentDao.findStudentsByCourseId(8);

        assertTrue(studentList.isEmpty());
    }
}
