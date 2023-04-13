package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.StudentDao;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ComponentScan(basePackages = "com.foxminded.chendev.schoolconsoleapp")
@Sql(
        scripts = {"/sql/clear_tables.sql", "/sql/students_create.sql", "/sql/courses_create.sql",
                "/sql/students_courses_relation.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class StudentDaoImplTestIT {

    @Autowired
    private StudentDao studentDao;

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
        assertEquals(1, findedStudent.getStudentId());

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

        studentDao.deleteByID(1);

        Optional<Student> optionalStudent = studentDao.findById(1);

        assertFalse(optionalStudent.isPresent());
    }
}
