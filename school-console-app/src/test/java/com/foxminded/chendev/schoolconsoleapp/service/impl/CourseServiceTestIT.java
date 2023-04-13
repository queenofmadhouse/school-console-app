package com.foxminded.chendev.schoolconsoleapp.service.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.impl.CourseDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.dao.impl.StudentDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.entity.StudentCourseRelation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@ComponentScan(basePackages = "com.foxminded.chendev.schoolconsoleapp")
@Sql(
        scripts = {"/sql/clear_tables.sql",
                "/sql/students_create.sql",
                "/sql/groups_create.sql",
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
    void addStudentToCourseShouldAddStudentToCourseWhenInputValid() {

        Student student = Student.builder()
                .withStudentId(1)
                .withFirstName("Alex")
                .withLastName("Smith")
                .withGroupId(35)
                .build();

        courseService.addStudentToCourse(student, 5);

        List<StudentCourseRelation> studentCourseRelationList = courseDao.findStudentsByCourseID(5);

        assertFalse(studentCourseRelationList.isEmpty());
        assertEquals(1, studentCourseRelationList.size());
        assertEquals(1, studentCourseRelationList.get(0).getStudentId());
        assertEquals(5, studentCourseRelationList.get(0).getCourseId());
    }

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

        List<Course> foundCourses = courseService.findAll();

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
    void removeStudentFromCourseShouldRemoveAllRelationsById() {

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

        courseService.removeStudentFromCourse(3, 35);

        List<StudentCourseRelation> studentCourseRelationList = courseDao.findCoursesByStudentID(3);

        assertFalse(studentCourseRelationList.isEmpty());
        assertEquals(1, studentCourseRelationList.size());
        assertEquals(3, studentCourseRelationList.get(0).getStudentId());
        assertEquals(39, studentCourseRelationList.get(0).getCourseId());
    }

    @Test
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

        student1.setStudentId(1);
        student2.setStudentId(2);
        student3.setStudentId(3);

        courseDao.addStudentToCourse(student1, 1);
        courseDao.addStudentToCourse(student2, 1);
        courseDao.addStudentToCourse(student3, 2);

        List<Student> studentList = courseService.findAllStudentsByCourseName("Math");

        assertFalse(studentList.isEmpty());
        assertEquals(2, studentList.size());
        assertEquals("Joan", studentList.get(0).getFirstName());
        assertEquals("Roberts", studentList.get(0).getLastName());
        assertEquals("Fillip", studentList.get(1).getFirstName());
        assertEquals("Some", studentList.get(1).getLastName());
    }
}
