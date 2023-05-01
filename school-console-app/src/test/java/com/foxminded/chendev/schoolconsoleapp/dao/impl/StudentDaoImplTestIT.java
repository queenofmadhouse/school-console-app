package com.foxminded.chendev.schoolconsoleapp.dao.impl;

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
        StudentDaoImpl.class,
        CourseDaoImpl.class
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
class StudentDaoImplTestIT {

    @Autowired
    private StudentDaoImpl studentDao;

    @Autowired
    private CourseDaoImpl courseDao;


    @Test
    void saveShouldSaveInEntityInDataBase() {

        Student studentAlex = Student.builder()
                .withFirstName("Alex")
                .withLastName("Kapranos")
                .withGroupId(1)
                .build();

        studentDao.save(studentAlex);

        Student foundStudent = studentDao.findById(1).orElse(null);

        assertNotNull(foundStudent);
        assertEquals(studentAlex.getFirstName(), foundStudent.getFirstName());
        assertEquals(studentAlex.getLastName(), foundStudent.getLastName());
        assertEquals(1, foundStudent.getUserId());

    }

    @Test
    void findAllShouldReturnListOfStudentWhenFound() {

        Student studentAlex = Student.builder()
                .withFirstName("Alex")
                .withLastName("Kapranos")
                .withGroupId(1)
                .build();

        Student studentNikol = Student.builder()
                .withFirstName("Nikol")
                .withLastName("Smith")
                .withGroupId(1)
                .build();

        studentDao.save(studentAlex);
        studentDao.save(studentNikol);

        List<Student> foundStudentList = studentDao.findAll();

        assertFalse(foundStudentList.isEmpty());
        assertEquals(2, foundStudentList.size());
    }

    @Test
    void updateShouldUpdateValues() {

        Student student = Student.builder()
                .withFirstName("Alex")
                .withLastName("Kapranos")
                .withGroupId(1)
                .build();

        studentDao.save(student);

        Student foundStudent = studentDao.findById(1).orElse(null);

        foundStudent.setFirstName("Alexandr");
        foundStudent.setLastName("Kirieshkin");

        studentDao.update(foundStudent);

        Student foundUpdatedStudent = studentDao.findById(1).orElse(null);

        assertNotNull(foundUpdatedStudent);
        assertEquals(foundStudent.getFirstName(), foundUpdatedStudent.getFirstName());
        assertEquals(foundStudent.getLastName(), foundUpdatedStudent.getLastName());
    }

    @Test
    void deleteByIDShouldDelete() {

        Student studentAlex = Student.builder()
                .withFirstName("Alex")
                .withLastName("Smith")
                .withGroupId(1)
                .build();

        int studentId = 1;

        studentDao.save(studentAlex);

        studentDao.deleteById(studentId);

        Optional<Student> optionalStudent = studentDao.findById(studentId);

        assertFalse(optionalStudent.isPresent());
    }

    @Test
    void addStudentToCourseShouldCreateRelationBetweenCourseAndStudent() {

        Student studentAlex = Student.builder()
                .withFirstName("Alex")
                .withLastName("Smith")
                .withGroupId(35)
                .build();

        Course courseMath = Course.builder()
                .withCourseName("Math")
                .withCourseDescription("hard")
                .build();

        studentDao.save(studentAlex);

        courseDao.save(courseMath);

        studentDao.addStudentToCourse(1, 1);

        List<Student> foundStudentList = studentDao.findStudentsByCourseId(1);

        assertFalse(foundStudentList.isEmpty());
        assertEquals(1, foundStudentList.size());
        assertEquals(1, foundStudentList.get(0).getUserId());
        assertEquals(studentAlex.getFirstName(), foundStudentList.get(0).getFirstName());
    }

    @Test
    void findStudentsByCourseIDShouldReturnListOfStudentCourseRelationEntity() {

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

        Student studentHira = Student.builder()
                .withFirstName("Hira")
                .withLastName("Stamps")
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

        studentDao.save(studentAlex);
        studentDao.save(studentBoyana);
        studentDao.save(studentHira);

        courseDao.save(courseMath);
        courseDao.save(courseBiology);

        studentDao.addStudentToCourse(1, 1);
        studentDao.addStudentToCourse(2, 1);
        studentDao.addStudentToCourse(3, 2);

        List<Student> foundStudentList = studentDao.findStudentsByCourseId(1);

        assertFalse(foundStudentList.isEmpty());
        assertEquals(2, foundStudentList.size());
        assertEquals(1, foundStudentList.get(0).getUserId());
        assertEquals(studentAlex.getFirstName(), foundStudentList.get(0).getFirstName());
        assertEquals(2, foundStudentList.get(1).getUserId());
        assertEquals(studentBoyana.getFirstName(), foundStudentList.get(1).getFirstName());
    }

    @Test
    void deleteRelationByStudentIDShouldDeleteOneRelation() {

        Student studentHira = Student.builder()
                .withFirstName("Hira")
                .withLastName("Stamps")
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

        studentDao.save(studentHira);

        courseDao.save(courseMath);
        courseDao.save(courseBiology);

        studentDao.addStudentToCourse(1, 1);
        studentDao.addStudentToCourse(1, 2);

        studentDao.removeStudentFromCourse(1, 2);

        List<Course> foundCourseList = courseDao.findCoursesByStudentId(1);

        assertFalse(foundCourseList.isEmpty());
        assertEquals(1, foundCourseList.size());
        assertEquals(1, foundCourseList.get(0).getCourseId());
        assertEquals(courseMath.getCourseName(), foundCourseList.get(0).getCourseName());
    }

    @Test
    void deleteAllRelationsByStudentIDShouldDeleteAllRelationsRelatedToStudentID() {

        studentDao.addStudentToCourse(1, 2);
        studentDao.addStudentToCourse(1, 3);
        studentDao.addStudentToCourse(1, 7);
        studentDao.addStudentToCourse(1, 20);

        studentDao.deleteAllRelationsByStudentId(3);

        List<Course> emptyCourseList = courseDao.findCoursesByStudentId(1);

        assertTrue(emptyCourseList.isEmpty());
    }

    @Test
    void removeStudentFromCourseShouldDeleteStudentFromCourse() {

        Student studentAlex = Student.builder()
                .withFirstName("Alex")
                .withLastName("Smith")
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

        studentDao.save(studentAlex);

        courseDao.save(courseMath);
        courseDao.save(courseBiology);
        courseDao.save(courseArt);

        studentDao.addStudentToCourse(1, 1);
        studentDao.addStudentToCourse(1, 2);
        studentDao.addStudentToCourse(1, 3);

        studentDao.removeStudentFromCourse(1, 2);

        List<Course> foundCoursesList = courseDao.findCoursesByStudentId(1);

        assertFalse(foundCoursesList.isEmpty());
        assertEquals(2, foundCoursesList.size());
        assertEquals(1, foundCoursesList.get(0).getCourseId());
        assertEquals(courseMath.getCourseName(), foundCoursesList.get(0).getCourseName());
        assertEquals(3, foundCoursesList.get(1).getCourseId());
        assertEquals(courseArt.getCourseName(), foundCoursesList.get(1).getCourseName());
    }

    @Test
    void deleteByIdShouldNotThrowExceptionWhenIdNotExist() {

        assertDoesNotThrow(() -> studentDao.deleteById(10));
    }
}
