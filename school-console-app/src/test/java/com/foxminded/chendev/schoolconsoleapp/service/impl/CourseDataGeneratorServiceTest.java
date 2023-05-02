package com.foxminded.chendev.schoolconsoleapp.service.impl;

import com.foxminded.chendev.schoolconsoleapp.datagenerator.impl.CourseDataGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;

@SpringBootTest
class CourseDataGeneratorServiceTest {

    @MockBean
    private CourseDataGenerator courseDataGenerator;

    @Autowired
    private CourseDataGeneratorService courseDataGeneratorService;

    @Test
    void generateDataShouldCallGenerateDataMethodFromCourseDataGenerator() {

        courseDataGeneratorService.generateData();

        verify(courseDataGenerator).generateData();
    }
}
