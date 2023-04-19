package com.foxminded.chendev.schoolconsoleapp.service.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.impl.CourseDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.dao.impl.StudentDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.service.StudentService;
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
                "/sql/courses_create.sql",
                "/sql/students_courses_relation.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class StudentServiceImplTesIT {

    @Autowired
    StudentService studentService;

    @Autowired
    CourseDaoImpl courseDao;

    @Autowired
    StudentDaoImpl studentDao;

    @Test
    void deleteByIDShouldDeleteAllRelations() {

        Course course1 = Course.builder()
                .withCourseName("Math")
                .withCourseDescription("Hard")
                .build();

        Course course2 = Course.builder()
                .withCourseName("Art")
                .withCourseDescription("Easy")
                .build();

        Student student = Student.builder()
                .withFirstName("Jane")
                .withLastName("Fershdeks")
                .build();

        courseDao.save(course1);
        courseDao.save(course2);

        studentDao.save(student);

        studentDao.addStudentToCourse(1, 1);
        studentDao.addStudentToCourse(1, 2);

        studentService.deleteById(1);

        Optional<Student> foundStudent = studentDao.findById(1);
        List<Course> courseList = courseDao.findCoursesByStudentId(1);

        assertFalse(foundStudent.isPresent());
        assertTrue(courseList.isEmpty());
    }

    @Test
    void saveShouldSaveObjectInDataBase() {

        Student student = Student.builder()
                .withFirstName("Alex")
                .withLastName("Kapranos")
                .withGroupId(1)
                .build();

        studentService.save(student);

        Student foundStudent = studentService.findById(1).orElse(null);

        assertNotNull(foundStudent);
        assertEquals("Alex", foundStudent.getFirstName());
        assertEquals("Kapranos", foundStudent.getLastName());
        assertEquals(1, foundStudent.getGroupId());
    }

    @Test
    void addStudentToCourseShouldAddStudentToCourseWhenInputValid() {

        Student student = Student.builder()
                .withUserId(1)
                .withFirstName("Alex")
                .withLastName("Smith")
                .withGroupId(35)
                .build();

        Course course = Course.builder()
                .withCourseName("Math")
                .withCourseDescription("Hard level")
                .build();

        studentDao.save(student);

        courseDao.save(course);

        studentService.addStudentToCourse(1, 1);

        List<Course> studentCourseRelationList = courseDao.findCoursesByStudentId(1);

        assertFalse(studentCourseRelationList.isEmpty());
        assertEquals(1, studentCourseRelationList.size());
        assertEquals(1, studentCourseRelationList.get(0).getCourseId());
        assertEquals("Math", studentCourseRelationList.get(0).getCourseName());
    }

    @Test
    void removeStudentFromCourseShouldRemoveAllRelationsById() {

        Student student = Student.builder()
                .withUserId(1)
                .withFirstName("Alex")
                .withLastName("Smith")
                .withGroupId(35)
                .build();

        Course course1 = Course.builder()
                .withCourseName("Math")
                .withCourseDescription("Hard")
                .build();

        Course course2 = Course.builder()
                .withCourseName("Math")
                .withCourseDescription("Hard")
                .build();

        studentDao.save(student);

        courseDao.save(course1);
        courseDao.save(course2);

        studentDao.addStudentToCourse(1, 1);
        studentDao.addStudentToCourse(1, 2);


        studentService.removeStudentFromCourse(1, 2);

        List<Course> courseList = courseDao.findCoursesByStudentId(1);

        assertFalse(courseList.isEmpty());
        assertEquals(1, courseList.size());
    }
}
