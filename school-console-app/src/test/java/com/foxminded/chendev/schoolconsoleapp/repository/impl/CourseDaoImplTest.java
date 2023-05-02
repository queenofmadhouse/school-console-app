//package com.foxminded.chendev.schoolconsoleapp.dao.impl;
//
//import com.foxminded.chendev.schoolconsoleapp.dao.CourseRepository;
//import com.foxminded.chendev.schoolconsoleapp.entity.Course;
//import com.foxminded.chendev.schoolconsoleapp.exception.DataBaseRuntimeException;
//import com.foxminded.chendev.schoolconsoleapp.initializer.ApplicationInitializer;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import javax.persistence.EntityManager;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.ArgumentMatchers.anyString;
//import static org.mockito.Mockito.doThrow;
//
//@SpringBootTest
//class CourseDaoImplTest {
//
//    @MockBean
//    private ApplicationInitializer applicationInitializer;
//
//    @MockBean
//    private EntityManager entityManager;
//
//    @Autowired
//    private CourseRepository courseDao;
//
//    @Test
//    void saveShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs() {
//
//        Course course = Course.builder().build();
//
//        doThrow(new RuntimeException())
//                .when(entityManager).persist(course);
//
//        assertThrows(DataBaseRuntimeException.class, () -> courseDao.save(course));
//    }
//
//    @Test
//    void updateShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs() {
//
//        Course course = Course.builder().build();
//
//        doThrow(new RuntimeException())
//                .when(entityManager).merge(course);
//
//        assertThrows(DataBaseRuntimeException.class, () -> courseDao.save(course));
//    }
//
//    @Test
//    void findAllShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs() {
//
//        doThrow(new RuntimeException())
//                .when(entityManager).createQuery(anyString());
//
//        assertThrows(DataBaseRuntimeException.class, () -> courseDao.findAll());
//    }
//
//    @Test
//    void findByIdShouldThrowDataBaseRunTimeExceptionWhenRunTimeExceptionOccurs() {
//
//        doThrow(new RuntimeException())
//                .when(entityManager).find(Course.class, 100L);
//
//        assertThrows(DataBaseRuntimeException.class, () -> courseDao.findById(100L));
//    }
//
//    @Test
//    void deleteByIdShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs() {
//
//        doThrow(new RuntimeException())
//                .when(entityManager).find(Course.class, 100L);
//
//        assertThrows(DataBaseRuntimeException.class, () -> courseDao.deleteById(100L));
//    }
//
//    @Test
//    void findCoursesByStudentIdShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs() {
//
//        doThrow(new RuntimeException())
//                .when(entityManager).createQuery(anyString());
//
//        assertThrows(DataBaseRuntimeException.class, () -> courseDao.findCoursesByStudentId(1L));
//    }
//
//    @Test
//    void deleteAllRelationsByCourseIdShouldThrowDataBaseRuntimeExceptionWhenDataAccessExceptionOccurs() {
//
//        doThrow(new RuntimeException())
//                .when(entityManager).createNativeQuery(anyString());
//
//        assertThrows(DataBaseRuntimeException.class, () -> courseDao.deleteAllRelationsByCourseId(1L));
//    }
//
//    @Test
//    void findCourseByNameShouldThrowDataBaseRuntimeExceptionWhenRuntimeExceptionOccurs() {
//
//        doThrow(new RuntimeException())
//                .when(entityManager).createNativeQuery(anyString());
//
//        assertThrows(DataBaseRuntimeException.class, () -> courseDao.findCourseByName("not exist"));
//    }
//}
