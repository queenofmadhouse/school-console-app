package com.foxminded.chendev.schoolconsoleapp.dao.impl;

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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"/sql/clear_tables.sql", "/sql/students_create.sql", "/sql/courses_create.sql",
        "/sql/students_courses_relation.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class StudentDaoImplTestIT {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private StudentDaoImpl studentDao;
    private CourseDaoImpl courseDao;
    private final String sqlQuery = "SELECT * FROM school.students WHERE first_name = ?";


    @BeforeEach
    void setUp() {

        studentDao = new StudentDaoImpl(jdbcTemplate);
        courseDao = new CourseDaoImpl(jdbcTemplate);
    }


    @Test
    void saveShouldSaveInEntityInDataBase() {

        Group group = Group.builder()
                .withGroupId(1)
                .build();

        Student student = Student.builder()
                .withFirstName("Alex")
                .withLastName("Kapranos")
                .withGroupId(1)
                .build();

        studentDao.save(student);

        Student findedStudent = studentDao.findById(1);

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
                .withGroupId(1)
                .build();

        studentDao.save(student);

        Student foundStudent = studentDao.findById(1);

        foundStudent.setFirstName("Alexandr");
        foundStudent.setLastName("Kirieshkin");

        studentDao.update(foundStudent);

        Student updatedStudent = studentDao.findById(1);

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
                .withGroupId(1)
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
