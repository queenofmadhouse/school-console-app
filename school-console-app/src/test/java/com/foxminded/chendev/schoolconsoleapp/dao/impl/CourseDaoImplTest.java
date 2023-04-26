package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.exception.DataBaseRuntimeException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
class CourseDaoImplTest {

    @MockBean
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CourseDaoImpl courseDao;

    @Test
    void saveShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs()
            throws NoSuchFieldException, IllegalAccessException {

        Course course = Course.builder().build();

        Field privateSqlForTest = CourseDaoImpl.class.getDeclaredField("INSERT_COURSE");

        privateSqlForTest.setAccessible(true);

        String sqlForTest = (String) privateSqlForTest.get(courseDao);

        when(jdbcTemplate.update(sqlForTest, courseDao.getSaveParameters(course)))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(DataBaseRuntimeException.class, () -> courseDao.save(course));
    }

    @Test
    void updateShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs()
            throws NoSuchFieldException, IllegalAccessException {

        Course course = Course.builder().build();

        Field privateSqlForTest = CourseDaoImpl.class.getDeclaredField("UPDATE_COURSE");

        privateSqlForTest.setAccessible(true);

        String sqlForTest = (String) privateSqlForTest.get(courseDao);

        when(jdbcTemplate.update(sqlForTest, courseDao.getUpdateParameters(course)))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(DataBaseRuntimeException.class, () -> courseDao.update(course));
    }

    @Test
    void findAllShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs()
            throws NoSuchFieldException, IllegalAccessException {

        Field privateSqlForTest = CourseDaoImpl.class.getDeclaredField("SELECT_ALL_COURSES");

        privateSqlForTest.setAccessible(true);

        String sqlForTest = (String) privateSqlForTest.get(courseDao);

        when(jdbcTemplate.query(sqlForTest, courseDao.getRowMapper()))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(DataBaseRuntimeException.class, () -> courseDao.findAll());
    }

    @Test
    void deleteByIdShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs()
            throws NoSuchFieldException, IllegalAccessException {

        Field privateSqlForTest = CourseDaoImpl.class.getDeclaredField("DELETE_COURSE_BY_ID");

        privateSqlForTest.setAccessible(true);

        String sqlForTest = (String) privateSqlForTest.get(courseDao);

        when(jdbcTemplate.update(sqlForTest, 1L))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(DataBaseRuntimeException.class, () -> courseDao.deleteById(1L));
    }

    @Test
    void findCoursesByStudentIdShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs()
            throws NoSuchFieldException, IllegalAccessException {

        Field privateSqlForTest = CourseDaoImpl.class.getDeclaredField("SELECT_ALL_COURSES_BY_STUDENT_ID");

        privateSqlForTest.setAccessible(true);

        String sqlForTest = (String) privateSqlForTest.get(courseDao);

        doThrow(new EmptyResultDataAccessException(1))
                .when(jdbcTemplate).query(sqlForTest, courseDao.getRowMapper(), 1L);

        assertThrows(DataBaseRuntimeException.class, () -> courseDao.findCoursesByStudentId(1L));
    }

    @Test
    void deleteAllRelationsByCourseIdShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs()
            throws NoSuchFieldException, IllegalAccessException {

        Field privateSqlForTest = CourseDaoImpl.class.getDeclaredField("DELETE_ALL_RELATIONS_BY_COURSE_ID");

        privateSqlForTest.setAccessible(true);

        String sqlForTest = (String) privateSqlForTest.get(courseDao);

        doThrow(new EmptyResultDataAccessException(1))
                .when(jdbcTemplate).update(sqlForTest, 1L);

        assertThrows(DataBaseRuntimeException.class, () -> courseDao.deleteAllRelationsByCourseId(1L));
    }
}
