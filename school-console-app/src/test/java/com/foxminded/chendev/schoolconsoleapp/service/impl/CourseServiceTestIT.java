package com.foxminded.chendev.schoolconsoleapp.service.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.impl.CourseDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.dao.impl.StudentDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@Sql(
        scripts = {"/sql/clear_tables.sql",
                "/sql/users_create.sql",
                "/sql/students_create.sql",
                "/sql/courses_create.sql",
                "/sql/students_courses_relation.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class CourseServiceTestIT {

    @Autowired
    CourseServiceImpl courseService;

    @Autowired
    CourseDaoImpl courseDao;

    @Autowired
    StudentDaoImpl studentDao;

    @Test
    void findAllShouldReturnListWithAllCoursesWhenInputValid() {

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

        List<Course> foundCourses = courseService.findAllCourses();

        assertFalse(foundCourses.isEmpty());
        assertEquals(3, foundCourses.size());
        assertEquals("Math", foundCourses.get(0).getCourseName());
        assertEquals("Hard level", foundCourses.get(0).getCourseDescription());
        assertEquals("Biology", foundCourses.get(1).getCourseName());
        assertEquals("Middle level", foundCourses.get(1).getCourseDescription());
        assertEquals("Java", foundCourses.get(2).getCourseName());
        assertEquals("Super hard level", foundCourses.get(2).getCourseDescription());
    }

    @Test
    @Transactional
    void findAllStudentsByCourseNameShouldReturnListOfStudentsFoundByCourseName() {

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
                .withCourseDescription("Hard level")
                .build();

        Course course2 = Course.builder()
                .withCourseName("Biology")
                .withCourseDescription("Middle level")
                .build();

        studentDao.save(student1);
        studentDao.save(student2);
        studentDao.save(student3);

        courseDao.save(course1);
        courseDao.save(course2);

        student1.setUserId(1);
        student2.setUserId(2);
        student3.setUserId(3);

        studentDao.addStudentToCourse(1, 1);
        studentDao.addStudentToCourse(2, 1);
        studentDao.addStudentToCourse(3, 2);

        List<Student> studentList = courseService.findAllStudentsByCourseName("Math");

        assertFalse(studentList.isEmpty());
        assertEquals(2, studentList.size());
        assertEquals("Joan", studentList.get(0).getFirstName());
        assertEquals("Roberts", studentList.get(0).getLastName());
        assertEquals("Fillip", studentList.get(1).getFirstName());
        assertEquals("Some", studentList.get(1).getLastName());
    }

    @Test
    @Transactional
    void findAllStudentsByCourseNameShouldReturnEmptyListIfNotFoundByCourseName() {

        List<Student> studentList = courseService.findAllStudentsByCourseName("Math");

        assertTrue(studentList.isEmpty());
    }
}
