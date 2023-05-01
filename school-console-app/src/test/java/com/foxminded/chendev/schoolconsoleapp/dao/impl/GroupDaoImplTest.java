package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.entity.Group;
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
class GroupDaoImplTest {

    @MockBean
    private ApplicationInitializer applicationInitializer;

    @MockBean
    private EntityManager entityManager;

    @Autowired
    private GroupDaoImpl groupDao;

    @Test
    void saveShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs() {

        Group group = Group.builder().build();

        doThrow(new RuntimeException())
                .when(entityManager).persist(group);

        assertThrows(DataBaseRuntimeException.class, () -> groupDao.save(group));
    }

    @Test
    void updateShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs() {

        Group group = Group.builder().build();

        doThrow(new RuntimeException())
                .when(entityManager).merge(group);

        assertThrows(DataBaseRuntimeException.class, () -> groupDao.update(group));
    }

    @Test
    void findAllShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs() {

        doThrow(new RuntimeException())
                .when(entityManager).createQuery(anyString());

        assertThrows(DataBaseRuntimeException.class, () -> groupDao.findAll());
    }

    @Test
    void findByIdShouldThrowDataBaseRunTimeExceptionWhenRunTimeExceptionOccurs() {

        doThrow(new RuntimeException())
                .when(entityManager).find(Group.class, 100L);

        assertThrows(DataBaseRuntimeException.class, () -> groupDao.findById(100L));
    }

    @Test
    void deleteByIdShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs() {

        doThrow(new RuntimeException())
                .when(entityManager).find(Group.class, 100L);

        assertThrows(DataBaseRuntimeException.class, () -> groupDao.deleteById(100L));
    }
    @Test
    void findGroupsWithLessOrEqualStudentsShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs() {

        doThrow(new RuntimeException())
                .when(entityManager).createNativeQuery(anyString());

        assertThrows(DataBaseRuntimeException.class, () -> groupDao.findGroupsWithLessOrEqualStudents(100L));
    }
}
