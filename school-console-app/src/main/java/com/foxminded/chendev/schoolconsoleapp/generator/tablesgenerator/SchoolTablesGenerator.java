package com.foxminded.chendev.schoolconsoleapp.generator.tablesgenerator;

public class SchoolTablesGenerator {

    private final String studentCreateQuery;
    private final String groupsCreateQuery;
    private final String coursesCreateQuery;
    private final String studentsCoursesRelationCreationQuery;
    private final TableGenerator tableGenerator;


    public SchoolTablesGenerator(TableGenerator tableGenerator, String studentCreateQuery, String groupsCreateQuery,
                                 String coursesCreateQuery, String studentsCoursesRelationCreationQuery) {
        this.tableGenerator = tableGenerator;
        this.studentCreateQuery = studentCreateQuery;
        this.groupsCreateQuery = groupsCreateQuery;
        this.coursesCreateQuery = coursesCreateQuery;
        this.studentsCoursesRelationCreationQuery = studentsCoursesRelationCreationQuery;
    }

    public void createTables() {
        tableGenerator.generateTable(studentCreateQuery);
        tableGenerator.generateTable(coursesCreateQuery);
        tableGenerator.generateTable(groupsCreateQuery);
        tableGenerator.generateTable(studentsCoursesRelationCreationQuery);
    }
}
