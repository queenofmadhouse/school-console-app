package com.foxminded.chendev.schoolconsoleapp.service.impl;

import com.foxminded.chendev.schoolconsoleapp.datagenerator.impl.StudentCourseRelationDataGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;

@SpringBootTest
class StudentCourseRelationDataGeneratorServiceTest {

    @MockBean
    private StudentCourseRelationDataGenerator studentCourseRelationDataGenerator;

    @Autowired
    private StudentCourseRelationDataGeneratorService studentCourseRelationDataGeneratorService;

    @Test
    void generateDataShouldCallGenerateDataMethodFromStudentCourseRelationDataGenerator() {

        studentCourseRelationDataGeneratorService.generateData();

        verify(studentCourseRelationDataGenerator).generateData();
    }
}
