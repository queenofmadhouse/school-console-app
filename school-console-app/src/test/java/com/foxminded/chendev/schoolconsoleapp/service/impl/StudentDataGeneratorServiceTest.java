package com.foxminded.chendev.schoolconsoleapp.service.impl;

import com.foxminded.chendev.schoolconsoleapp.datagenerator.impl.StudentDataGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;

@SpringBootTest
class StudentDataGeneratorServiceTest {

    @MockBean
    private StudentDataGenerator studentDataGenerator;

    @Autowired
    private StudentDataGeneratorService studentDataGeneratorService;

    @Test
    void generateDataShouldCallGenerateDataMethodFromStudentDataGenerator() {

        studentDataGeneratorService.generateData();

        verify(studentDataGenerator).generateData();
    }
}
