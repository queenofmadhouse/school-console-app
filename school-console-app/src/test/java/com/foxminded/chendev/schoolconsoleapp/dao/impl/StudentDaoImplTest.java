package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.exception.DataBaseRuntimeException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentDaoImplTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private StudentDaoImpl studentDao;

    @Test
    void saveShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs()
            throws NoSuchFieldException, IllegalAccessException {

        Student student = Student.builder().build();

        Field privateSqlForTest = StudentDaoImpl.class.getDeclaredField("INSERT_USER_AND_STUDENT");

        privateSqlForTest.setAccessible(true);

        String sqlForTest = (String) privateSqlForTest.get(studentDao);

        when(jdbcTemplate.update(sqlForTest, studentDao.getSaveParameters(student)))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(DataBaseRuntimeException.class, () -> studentDao.save(student));
    }

    @Test
    void updateShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs()
            throws NoSuchFieldException, IllegalAccessException {

        Student student = Student.builder().build();

        Field privateSqlForTest = StudentDaoImpl.class.getDeclaredField("UPDATE_STUDENT");

        privateSqlForTest.setAccessible(true);

        String sqlForTest = (String) privateSqlForTest.get(studentDao);

        when(jdbcTemplate.update(sqlForTest, studentDao.getUpdateParameters(student)))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(DataBaseRuntimeException.class, () -> studentDao.update(student));
    }

    @Test
    void findAllShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs()
            throws NoSuchFieldException, IllegalAccessException {

        Field privateSqlForTest = StudentDaoImpl.class.getDeclaredField("SELECT_ALL_STUDENTS");

        privateSqlForTest.setAccessible(true);

        String sqlForTest = (String) privateSqlForTest.get(studentDao);

        when(jdbcTemplate.query(sqlForTest, studentDao.getRowMapper()))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(DataBaseRuntimeException.class, () -> studentDao.findAll());
    }

    @Test
    void deleteByIdShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs()
            throws NoSuchFieldException, IllegalAccessException {

        Field privateSqlForTest = StudentDaoImpl.class.getDeclaredField("DELETE_STUDENT_BY_ID");

        privateSqlForTest.setAccessible(true);

        String sqlForTest = (String) privateSqlForTest.get(studentDao);

        when(jdbcTemplate.update(sqlForTest, 100))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(DataBaseRuntimeException.class, () -> studentDao.deleteById(100));
    }

    @Test
    void removeStudentFromCourseShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs()
            throws NoSuchFieldException, IllegalAccessException {

        Field privateSqlForTest = StudentDaoImpl.class.getDeclaredField("DELETE_RELATION_BY_STUDENT_ID");

        privateSqlForTest.setAccessible(true);

        String sqlForTest = (String) privateSqlForTest.get(studentDao);

        when(jdbcTemplate.update(sqlForTest, 1L, 1L))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(DataBaseRuntimeException.class, () -> studentDao.removeStudentFromCourse(1L,1L));
    }

    @Test
    void addStudentToCourseShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs()
            throws NoSuchFieldException, IllegalAccessException {

        Field privateSqlForTest = StudentDaoImpl.class.getDeclaredField("INSERT_COURSE_RELATION");

        privateSqlForTest.setAccessible(true);

        String sqlForTest = (String) privateSqlForTest.get(studentDao);

        when(jdbcTemplate.update(sqlForTest, 1L, 1L))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(DataBaseRuntimeException.class, () -> studentDao.addStudentToCourse(1L,1L));
    }

    @Test
    void findStudentsByCourseIdShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs()
            throws NoSuchFieldException, IllegalAccessException {

        Field privateSqlForTest = StudentDaoImpl.class.getDeclaredField("SELECT_ALL_STUDENTS_BY_COURSE_ID");

        privateSqlForTest.setAccessible(true);

        String sqlForTest = (String) privateSqlForTest.get(studentDao);

        when(jdbcTemplate.query(sqlForTest, studentDao.getRowMapper(),1L))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(DataBaseRuntimeException.class, () -> studentDao.findStudentsByCourseId(1L));
    }

    @Test
    void deleteAllRelationsByStudentIdShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs()
            throws NoSuchFieldException, IllegalAccessException {

        Field privateSqlForTest = StudentDaoImpl.class.getDeclaredField("DELETE_ALL_RELATIONS_BY_STUDENT_ID");

        privateSqlForTest.setAccessible(true);

        String sqlForTest = (String) privateSqlForTest.get(studentDao);

        when(jdbcTemplate.update(sqlForTest, 1L))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(DataBaseRuntimeException.class, () -> studentDao.deleteAllRelationsByStudentId(1L));
    }

    @Test
    void deleteRelationByStudentIdShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs()
            throws NoSuchFieldException, IllegalAccessException {

        Field privateSqlForTest = StudentDaoImpl.class.getDeclaredField("DELETE_RELATION_BY_STUDENT_ID");

        privateSqlForTest.setAccessible(true);

        String sqlForTest = (String) privateSqlForTest.get(studentDao);

        when(jdbcTemplate.update(sqlForTest, 1L, 1L))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(DataBaseRuntimeException.class, () -> studentDao.deleteRelationByStudentId(1L, 1L));
    }
}
