package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.exception.DataBaseRuntimeException;
import com.foxminded.chendev.schoolconsoleapp.initializer.ApplicationInitializer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
class StudentDaoImplTest {

    @MockBean
    private ApplicationInitializer applicationInitializer;

    @MockBean
    private EntityManager entityManager;

    @Autowired
    private StudentDaoImpl studentDao;

    @Test
    void saveShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs() {

        Student student = Student.builder().build();

        doThrow(new RuntimeException())
                .when(entityManager).persist(student);

        assertThrows(DataBaseRuntimeException.class, () -> studentDao.save(student));
    }

    @Test
    void updateShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs() {

        Student student = Student.builder().build();

        doThrow(new RuntimeException())
                .when(entityManager).merge(student);

        assertThrows(DataBaseRuntimeException.class, () -> studentDao.update(student));
    }

    @Test
    void findAllShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs() {

        doThrow(new RuntimeException())
                .when(entityManager).createQuery(anyString());

        assertThrows(DataBaseRuntimeException.class, () -> studentDao.findAll());
    }

    @Test
    void findByIdShouldThrowDataBaseRunTimeExceptionWhenRunTimeExceptionOccurs() {

        doThrow(new RuntimeException())
                .when(entityManager).find(Student.class, 100L);

        assertThrows(DataBaseRuntimeException.class, () -> studentDao.findById(100L));
    }

    @Test
    void deleteByIdShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs() {

        doThrow(new RuntimeException())
                .when(entityManager).find(Student.class, 100L);

        assertThrows(DataBaseRuntimeException.class, () -> studentDao.deleteById(100L));
    }

    @Test
    void removeStudentFromCourseShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs() {

        doThrow(new RuntimeException())
                .when(entityManager).createNativeQuery(anyString());

                assertThrows(DataBaseRuntimeException.class, () -> studentDao.removeStudentFromCourse(1L,1L));
    }

    @Test
    void addStudentToCourseShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs() {

        doThrow(new RuntimeException())
                .when(entityManager).createNativeQuery(anyString());

        assertThrows(DataBaseRuntimeException.class, () -> studentDao.addStudentToCourse(1L,1L));
    }

    @Test
    void findStudentsByCourseIdShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs() {

        doThrow(new RuntimeException())
                .when(entityManager).createQuery(anyString());

        assertThrows(DataBaseRuntimeException.class, () -> studentDao.findStudentsByCourseId(1L));
    }

    @Test
    void deleteAllRelationsByStudentIdShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs() {

        doThrow(new RuntimeException())
                .when(entityManager).createNativeQuery(anyString());

        assertThrows(DataBaseRuntimeException.class, () -> studentDao.deleteAllRelationsByStudentId(1L));
    }
}
