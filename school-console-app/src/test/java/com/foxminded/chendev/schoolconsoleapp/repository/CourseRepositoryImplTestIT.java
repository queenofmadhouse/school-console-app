package com.foxminded.chendev.schoolconsoleapp.repository;

import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        CourseRepository.class,
        StudentRepository.class
}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"/sql/clear_tables.sql",
                "/sql/users_create.sql",
                "/sql/students_create.sql",
                "/sql/groups_create.sql",
                "/sql/courses_create.sql",
                "/sql/students_courses_relation.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class CourseRepositoryImplTestIT {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void findCourseByCourseNameShouldReturnCourse() {

        Course courseMath = Course.builder()
                .withCourseName("Math")
                .withCourseDescription("Hard level")
                .build();

        Course courseBiology = Course.builder()
                .withCourseName("Biology")
                .withCourseDescription("Middle level")
                .build();

        Course courseJava = Course.builder()
                .withCourseName("Java")
                .withCourseDescription("Super hard level")
                .build();

        courseRepository.save(courseMath);
        courseRepository.save(courseBiology);
        courseRepository.save(courseJava);

        Course foundCourse = courseRepository.findByCourseName("Java").orElse(null);

        assertNotNull(foundCourse);
        assertEquals(3, foundCourse.getCourseId());
        assertEquals(courseJava.getCourseName(), foundCourse.getCourseName());
        assertEquals(courseJava.getCourseDescription(), foundCourse.getCourseDescription());
    }

    @Test
    void deleteByIdShouldDelete() {

        Course courseMath = Course.builder()
                .withCourseName("Math")
                .withCourseDescription("Something hard")
                .build();

        courseRepository.save(courseMath);

        courseRepository.deleteByCourseId(1);

        Optional<Course> optionalCourse = courseRepository.findByCourseId(1);

        assertFalse(optionalCourse.isPresent());
    }

    @Test
    void saveShouldUpdateValues() {

        Course courseNewJavaCourse = Course.builder()
                .withCourseName("New Java Course")
                .withCourseDescription("With old information")
                .build();

        courseRepository.save(courseNewJavaCourse);

        Course foundCourse = courseRepository.findByCourseName("New Java Course").orElse(null);

        foundCourse.setCourseName("Old Java Course");
        foundCourse.setCourseDescription("Without info at all");

        courseRepository.save(foundCourse);

        Course foundUpdatedCourse = courseRepository.findByCourseId(1).orElse(null);

        assertNotNull(foundUpdatedCourse);
        assertEquals(foundCourse.getCourseName(), foundUpdatedCourse.getCourseName());
        assertEquals(foundCourse.getCourseDescription(), foundUpdatedCourse.getCourseDescription());
    }

    @Test
    void findAllCoursesShouldFindAllCoursesAndReturnList() {

        Course courseMath = Course.builder()
                .withCourseName("Math")
                .withCourseDescription("Something hard")
                .build();

        Course courseBiology = Course.builder()
                .withCourseName("Biology")
                .withCourseDescription("Something hard")
                .build();

        Course courseArt = Course.builder()
                .withCourseName("Art")
                .withCourseDescription("Something hard")
                .build();

        courseRepository.save(courseMath);
        courseRepository.save(courseBiology);
        courseRepository.save(courseArt);

        List<Course> courseList = courseRepository.findAll();

        assertNotNull(courseList);
        assertEquals(3, courseList.size());
        assertEquals(courseMath.getCourseName(), courseList.get(0).getCourseName());
        assertEquals(courseMath.getCourseDescription(), courseList.get(0).getCourseDescription());
        assertEquals(courseBiology.getCourseName(), courseList.get(1).getCourseName());
        assertEquals(courseBiology.getCourseDescription(), courseList.get(1).getCourseDescription());
        assertEquals(courseArt.getCourseName(), courseList.get(2).getCourseName());
        assertEquals(courseArt.getCourseDescription(), courseList.get(2).getCourseDescription());
    }

    @Test
    void findCoursesByStudentIDShouldReturnListOfStudentCourseRelationEntity() {

        Student studentAlex = Student.builder()
                .withFirstName("Alex")
                .withLastName("Smith")
                .withGroupId(35)
                .build();

        Student studentBoyana = Student.builder()
                .withFirstName("Boyana")
                .withLastName("Arbams")
                .withGroupId(35)
                .build();

        Course courseMath = Course.builder()
                .withCourseName("Math")
                .withCourseDescription("Something hard")
                .build();

        Course courseBiology = Course.builder()
                .withCourseName("Biology")
                .withCourseDescription("Something hard")
                .build();

        Course courseArt = Course.builder()
                .withCourseName("Art")
                .withCourseDescription("Something hard")
                .build();

        studentRepository.save(studentAlex);
        studentRepository.save(studentBoyana);

        courseRepository.save(courseMath);
        courseRepository.save(courseBiology);
        courseRepository.save(courseArt);

        studentRepository.addStudentToCourse(1, 1);
        studentRepository.addStudentToCourse(1, 2);
        studentRepository.addStudentToCourse(2, 3);

        List<Course> courseList = courseRepository.findCoursesByStudentId(1);

        assertFalse(courseList.isEmpty());
        assertEquals(2, courseList.size());
        assertEquals(1, courseList.get(0).getCourseId());
        assertEquals(courseMath.getCourseName(), courseList.get(0).getCourseName());
        assertEquals(2, courseList.get(1).getCourseId());
        assertEquals(courseBiology.getCourseName(), courseList.get(1).getCourseName());
    }

    @Test
    void deleteAllRelationsByCourseIDShouldDeleteAllRelationsRelatedToCourseID() {

        studentRepository.addStudentToCourse(5, 8);
        studentRepository.addStudentToCourse(3, 8);
        studentRepository.addStudentToCourse(10, 8);
        studentRepository.addStudentToCourse(200, 8);
        studentRepository.addStudentToCourse(1, 8);

        courseRepository.deleteAllRelationsByCourseId(8);

        List<Student> studentList = studentRepository.findStudentsByCourseId(8);

        assertTrue(studentList.isEmpty());
    }

    @Test
    void findCourseByCourseNameShouldReturnEmptyOptionalWhenNotPresent() {

        Optional<Course> course = courseRepository.findByCourseName("Not Present");

        assertFalse(course.isPresent());
    }

    @Test
    void deleteByIdShouldNotThrowDatabaseRuntimeExceptionWhenNotFound() {

        assertDoesNotThrow(() -> courseRepository.deleteByCourseId(100));
    }
}
