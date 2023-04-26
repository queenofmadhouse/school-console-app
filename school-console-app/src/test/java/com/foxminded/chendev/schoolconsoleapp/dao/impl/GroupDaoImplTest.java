package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import com.foxminded.chendev.schoolconsoleapp.exception.DataBaseRuntimeException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
class GroupDaoImplTest {

    @MockBean
    private JdbcTemplate jdbcTemplate;

    @MockBean
    private Logger groupLogger;

    @Autowired
    private GroupDaoImpl groupDao;

    @Test
    void saveShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs()
            throws NoSuchFieldException, IllegalAccessException {

        Group group = Group.builder().build();

        Field privateSqlForTest = GroupDaoImpl.class.getDeclaredField("INSERT_GROUP");

        privateSqlForTest.setAccessible(true);

        String sqlForTest = (String) privateSqlForTest.get(groupDao);

        when(jdbcTemplate.update(sqlForTest, groupDao.getSaveParameters(group)))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(DataBaseRuntimeException.class, () -> groupDao.save(group));
    }

    @Test
    void updateShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs()
            throws NoSuchFieldException, IllegalAccessException {

        Group group = Group.builder().build();

        Field privateSqlForTest = GroupDaoImpl.class.getDeclaredField("UPDATE_GROUP");

        privateSqlForTest.setAccessible(true);

        String sqlForTest = (String) privateSqlForTest.get(groupDao);

        when(jdbcTemplate.update(sqlForTest, groupDao.getUpdateParameters(group)))
                .thenThrow(new EmptyResultDataAccessException(1));
        assertThrows(DataBaseRuntimeException.class, () -> groupDao.update(group));
    }

    @Test
    void findAllShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs()
            throws NoSuchFieldException, IllegalAccessException {

        Field privateSqlForTest = GroupDaoImpl.class.getDeclaredField("SELECT_ALL_GROUPS");

        privateSqlForTest.setAccessible(true);

        String sqlForTest = (String) privateSqlForTest.get(groupDao);

        when(jdbcTemplate.query(sqlForTest, groupDao.getRowMapper()))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(DataBaseRuntimeException.class, () -> groupDao.findAll());
    }

    @Test
    void deleteByIdShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs()
            throws NoSuchFieldException, IllegalAccessException {

        Field privateSqlForTest = GroupDaoImpl.class.getDeclaredField("DELETE_GROUP_BY_ID");

        privateSqlForTest.setAccessible(true);

        String sqlForTest = (String) privateSqlForTest.get(groupDao);

        when(jdbcTemplate.update(sqlForTest, 1L))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(DataBaseRuntimeException.class, () -> groupDao.deleteById(1L));
    }

    @Test
    void findGroupsWithLessOrEqualStudentsShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs()
            throws NoSuchFieldException, IllegalAccessException {

        Field privateSqlForTest = GroupDaoImpl.class.getDeclaredField("SELECT_GROUPS_WITH_LESS_OR_EQUALS_STUDENTS");

        privateSqlForTest.setAccessible(true);

        String sqlForTest = (String) privateSqlForTest.get(groupDao);

        when(jdbcTemplate.query(sqlForTest, new Object[]{1L}, groupDao.getRowMapper()))
                .thenThrow(new EmptyResultDataAccessException(1));

        assertThrows(DataBaseRuntimeException.class, () -> groupDao.findGroupsWithLessOrEqualStudents(1L));
    }
}
