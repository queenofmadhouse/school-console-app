package com.foxminded.chendev.schoolconsoleapp.datagenerator.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.impl.CourseDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CourseDataGeneratorTest {
    @Mock
    private CourseDaoImpl courseDao;

    private CourseDataGenerator coursesGenerator;

    private int amountOfCourses;

    @BeforeEach
    void setUp() {
        amountOfCourses = 10;
        coursesGenerator = new CourseDataGenerator(courseDao, amountOfCourses);
    }

    @Test
    void shouldGenerateData() {

        coursesGenerator.generateData();

        verify(courseDao, times(amountOfCourses)).save(any(Course.class));
    }
}
