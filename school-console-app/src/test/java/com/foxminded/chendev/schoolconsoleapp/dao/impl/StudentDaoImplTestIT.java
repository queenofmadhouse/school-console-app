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
class StudentDaoImplTestIT {

    @Autowired
    private StudentDaoImpl studentDao;

    @Autowired
    private CourseDaoImpl courseDao;


    @Test
    void saveShouldSaveInEntityInDataBase() {

        Student student = Student.builder()
                .withFirstName("Alex")
                .withLastName("Kapranos")
                .withGroupId(1)
                .build();

        studentDao.save(student);

        Student findedStudent = studentDao.findById(1).orElse(null);

        assertNotNull(findedStudent);
        assertEquals("Alex", findedStudent.getFirstName());
        assertEquals("Kapranos", findedStudent.getLastName());
        assertEquals(1, findedStudent.getUserId());

    }

    @Test
    void findAllShouldReturnListOfStudentWhenFound() {

        Student student1 = Student.builder()
                .withFirstName("Alex")
                .withLastName("Kapranos")
                .withGroupId(1)
                .build();
        Student student2 = Student.builder()
                .withFirstName("Nikol")
                .withLastName("Smith")
                .withGroupId(1)
                .build();

        studentDao.save(student1);
        studentDao.save(student2);

        List<Student> resultList = studentDao.findAll();

        assertFalse(resultList.isEmpty());
        assertEquals(2, resultList.size());
    }

    @Test
    void updateShouldUpdateValues() {

        Student student = Student.builder()
                .withFirstName("Alex")
                .withLastName("Kapranos")
                .withGroupId(1)
                .build();

        studentDao.save(student);

        Student findedStudent = studentDao.findById(1).orElse(null);

        findedStudent.setFirstName("Alexandr");
        findedStudent.setLastName("Kirieshkin");

        studentDao.update(findedStudent);

        Student updatedStudent = studentDao.findById(1).orElse(null);

        assertNotNull(updatedStudent);
        assertEquals("Alexandr", updatedStudent.getFirstName());
        assertEquals("Kirieshkin", updatedStudent.getLastName());
    }

    @Test
    void deleteByIDShouldDelete() {

        Student student = Student.builder()
                .withFirstName("Alex")
                .withLastName("Smith")
                .withGroupId(1)
                .build();

        studentDao.save(student);

        studentDao.deleteById(1);

        Optional<Student> optionalStudent = studentDao.findById(1);

        assertFalse(optionalStudent.isPresent());
    }

    @Test
    void addStudentToCourseShouldCreateRelationBetweenCourseAndStudent() {

        Student student = Student.builder()
                .withFirstName("Alex")
                .withLastName("Smith")
                .withGroupId(35)
                .build();

        Course course = Course.builder()
                .withCourseName("Math")
                .withCourseDescription("hard")
                .build();

        studentDao.save(student);

        courseDao.save(course);

        studentDao.addStudentToCourse(1, 1);

        List<Student> studentList = studentDao.findStudentsByCourseId(1);

        assertFalse(studentList.isEmpty());
        assertEquals(1, studentList.size());
        assertEquals(1, studentList.get(0).getUserId());
        assertEquals("Alex", studentList.get(0).getFirstName());
    }

    @Test
    void findStudentsByCourseIDShouldReturnListOfStudentCourseRelationEntity() {

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

        Student student3 = Student.builder()
                .withFirstName("Hira")
                .withLastName("Stamps")
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

        studentDao.save(student1);
        studentDao.save(student2);
        studentDao.save(student3);

        courseDao.save(course1);
        courseDao.save(course2);

        studentDao.addStudentToCourse(1, 1);
        studentDao.addStudentToCourse(2, 1);
        studentDao.addStudentToCourse(3, 2);

        List<Student> studentList = studentDao.findStudentsByCourseId(1);

        assertFalse(studentList.isEmpty());
        assertEquals(2, studentList.size());
        assertEquals(1, studentList.get(0).getUserId());
        assertEquals("Alex", studentList.get(0).getFirstName());
        assertEquals(2, studentList.get(1).getUserId());
        assertEquals("Boyana", studentList.get(1).getFirstName());
    }

    @Test
    void deleteRelationByStudentIDShouldDeleteOneRelation() {

        Student student1 = Student.builder()
                .withFirstName("Hira")
                .withLastName("Stamps")
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

        studentDao.save(student1);

        courseDao.save(course1);
        courseDao.save(course2);

        studentDao.addStudentToCourse(1, 1);
        studentDao.addStudentToCourse(1, 2);

        studentDao.deleteRelationByStudentId(1, 2);

        List<Course> courseList = courseDao.findCoursesByStudentId(1);

        assertFalse(courseList.isEmpty());
        assertEquals(1, courseList.size());
        assertEquals(1, courseList.get(0).getCourseId());
        assertEquals("Math", courseList.get(0).getCourseName());
    }

    @Test
    void deleteAllRelationsByStudentIDShouldDeleteAllRelationsRelatedToStudentID() {

        studentDao.addStudentToCourse(1, 2);
        studentDao.addStudentToCourse(1, 3);
        studentDao.addStudentToCourse(1, 7);
        studentDao.addStudentToCourse(1, 20);

        studentDao.deleteAllRelationsByStudentId(3);

        List<Course> courseList = courseDao.findCoursesByStudentId(1);

        assertTrue(courseList.isEmpty());
    }

    @Test
    void removeStudentFromCourseShouldDeleteStudentFromCourse() {

        Student student = Student.builder()
                .withFirstName("Alex")
                .withLastName("Smith")
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

        studentDao.save(student);

        courseDao.save(course1);
        courseDao.save(course2);
        courseDao.save(course3);

        studentDao.addStudentToCourse(1, 1);
        studentDao.addStudentToCourse(1, 2);
        studentDao.addStudentToCourse(1, 3);

        studentDao.removeStudentFromCourse(1, 2);

        List<Course> studentCourseRelationList = courseDao.findCoursesByStudentId(1);

        assertFalse(studentCourseRelationList.isEmpty());
        assertEquals(2, studentCourseRelationList.size());
        assertEquals(1, studentCourseRelationList.get(0).getCourseId());
        assertEquals("Math", studentCourseRelationList.get(0).getCourseName());
        assertEquals(3, studentCourseRelationList.get(1).getCourseId());
        assertEquals("Art", studentCourseRelationList.get(1).getCourseName());
    }
}
