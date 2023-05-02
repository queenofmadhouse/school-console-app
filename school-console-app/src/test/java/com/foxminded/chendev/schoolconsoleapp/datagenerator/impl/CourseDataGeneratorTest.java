package com.foxminded.chendev.schoolconsoleapp.datagenerator.impl;

import com.foxminded.chendev.schoolconsoleapp.repository.CourseRepository;
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
    private CourseRepository courseRepository;

    private CourseDataGenerator coursesGenerator;

    private int amountOfCourses;

    @BeforeEach
    void setUp() {
        amountOfCourses = 10;
        coursesGenerator = new CourseDataGenerator(courseRepository, amountOfCourses);
    }

    @Test
    void shouldGenerateData() {

        coursesGenerator.generateData();

        verify(courseRepository, times(amountOfCourses)).save(any(Course.class));
    }
}
