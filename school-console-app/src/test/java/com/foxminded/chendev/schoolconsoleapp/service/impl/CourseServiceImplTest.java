package com.foxminded.chendev.schoolconsoleapp.service.impl;

import com.foxminded.chendev.schoolconsoleapp.exception.DataBaseRuntimeException;
import com.foxminded.chendev.schoolconsoleapp.repository.CourseRepository;
import com.foxminded.chendev.schoolconsoleapp.repository.StudentRepository;
import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@SpringBootTest
class CourseServiceImplTest {

    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private CourseServiceImpl courseService;

    @Test
    void findAllShouldReturnListWithAllCoursesWhenInputValid() {

        Course courseMath = Course.builder()
                .withCourseName("Math")
                .withCourseDescription("Hard level")
                .build();

        Course courseBiology = Course.builder()
                .withCourseName("Biology")
                .withCourseDescription("Middle level")
                .build();

        Course courseJava = Course.builder()
                .withCourseName("Java")
                .withCourseDescription("Super hard level")
                .build();

        List<Course> courseList = new ArrayList<>();

        courseList.add(courseMath);
        courseList.add(courseBiology);
        courseList.add(courseJava);

        when(courseRepository.findAll()).thenReturn(courseList);

        List<Course> foundCourses = courseService.findAllCourses();

        assertFalse(foundCourses.isEmpty());
        assertEquals(3, foundCourses.size());
        assertEquals("Math", foundCourses.get(0).getCourseName());
        assertEquals("Hard level", foundCourses.get(0).getCourseDescription());
        assertEquals("Biology", foundCourses.get(1).getCourseName());
        assertEquals("Middle level", foundCourses.get(1).getCourseDescription());
        assertEquals("Java", foundCourses.get(2).getCourseName());
        assertEquals("Super hard level", foundCourses.get(2).getCourseDescription());
    }

    @Test
    void findAllCoursesShouldThrowDataBaseRuntimeExceptionWhenExceptionOccurs() {

        doThrow(new RuntimeException()).when(courseRepository).findAll();

        assertThrows(DataBaseRuntimeException.class, () -> courseService.findAllCourses());
    }
}
