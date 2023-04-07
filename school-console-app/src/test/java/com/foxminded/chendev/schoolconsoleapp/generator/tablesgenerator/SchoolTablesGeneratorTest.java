package com.foxminded.chendev.schoolconsoleapp.generator.tablesgenerator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SchoolTablesGeneratorTest {

    private final String studentCreateQuery = "CREATE TABLE students (...);";
    private final String groupsCreateQuery = "CREATE TABLE groups (...);";
    private final String coursesCreateQuery = "CREATE TABLE courses (...);";
    private final String studentsCoursesRelationCreationQuery = "CREATE TABLE students_courses (...);";

    @Mock
    private TableGenerator tableGenerator;

    private SchoolTablesGenerator schoolTablesGenerator;

    @BeforeEach
    void setUp() {
        schoolTablesGenerator = new SchoolTablesGenerator(tableGenerator,
                studentCreateQuery,
                groupsCreateQuery,
                coursesCreateQuery,
                studentsCoursesRelationCreationQuery);
    }

    @Test
    void createTablesShouldCallsGenerateTableForAllQueries() {

        schoolTablesGenerator.createTables();

        Mockito.verify(tableGenerator).generateTable(studentCreateQuery);
        Mockito.verify(tableGenerator).generateTable(groupsCreateQuery);
        Mockito.verify(tableGenerator).generateTable(coursesCreateQuery);
        Mockito.verify(tableGenerator).generateTable(studentsCoursesRelationCreationQuery);
    }
}
