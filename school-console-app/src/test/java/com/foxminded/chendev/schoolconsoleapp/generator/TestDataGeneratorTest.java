package com.foxminded.chendev.schoolconsoleapp.generator;

import com.foxminded.chendev.schoolconsoleapp.generator.datagenegator.CoursesGenerator;
import com.foxminded.chendev.schoolconsoleapp.generator.datagenegator.GroupsGenerator;
import com.foxminded.chendev.schoolconsoleapp.generator.datagenegator.StudentsCoursesRelationsGenerator;
import com.foxminded.chendev.schoolconsoleapp.generator.datagenegator.StudentsGenerator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TestDataGeneratorTest {

    @Mock
    StudentsGenerator studentsGenerator;

    @Mock
    GroupsGenerator groupsGenerator;

    @Mock
    CoursesGenerator coursesGenerator;

    @Mock
    StudentsCoursesRelationsGenerator studentsCoursesRelationsGenerator;

    @InjectMocks
    private TestDataGenerator testDataGenerator;

    @Test
    void generateTestData_callsDependenciesInCorrectOrder() {

        testDataGenerator.generateTestData();

        Mockito.inOrder(
                groupsGenerator,
                coursesGenerator,
                studentsGenerator,
                studentsCoursesRelationsGenerator
        ).verify(groupsGenerator).generateData();
        Mockito.verify(coursesGenerator).generateData();
        Mockito.verify(studentsGenerator).generateData();
        Mockito.verify(studentsCoursesRelationsGenerator).generateData();
    }
}
