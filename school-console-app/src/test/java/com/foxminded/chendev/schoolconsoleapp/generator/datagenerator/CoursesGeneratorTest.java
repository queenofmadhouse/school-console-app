package com.foxminded.chendev.schoolconsoleapp.generator.datagenerator;

import com.foxminded.chendev.schoolconsoleapp.dao.impl.CourseDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.generator.datagenegator.CoursesGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CoursesGeneratorTest {

    @Mock
    private CourseDaoImpl courseDao;

    private CoursesGenerator coursesGenerator;

    private int amountOfCourses;

    @BeforeEach
    void setUp() {
        amountOfCourses = 5;
        coursesGenerator = new CoursesGenerator(courseDao, amountOfCourses);
    }

    @Test
    void shouldGenerateData() {
        // Act
        coursesGenerator.generateData();

        // Assert
        verify(courseDao, times(amountOfCourses)).save(any(Course.class));
    }
}
