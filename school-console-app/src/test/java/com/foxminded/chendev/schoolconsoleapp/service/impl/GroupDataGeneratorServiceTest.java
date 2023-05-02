package com.foxminded.chendev.schoolconsoleapp.service.impl;

import com.foxminded.chendev.schoolconsoleapp.datagenerator.impl.GroupDataGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.verify;

@SpringBootTest
class GroupDataGeneratorServiceTest {

    @MockBean
    private GroupDataGenerator groupDataGenerator;

    @Autowired
    private GroupDataGeneratorService groupDataGeneratorService;

    @Test
    void generateDataShouldCallGenerateDataMethodFromGroupDataGenerator() {

        groupDataGeneratorService.generateData();

        verify(groupDataGenerator).generateData();
    }
}
