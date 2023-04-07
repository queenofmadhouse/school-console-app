package com.foxminded.chendev.schoolconsoleapp.generator;

import com.foxminded.chendev.schoolconsoleapp.generator.datagenegator.CoursesGenerator;
import com.foxminded.chendev.schoolconsoleapp.generator.datagenegator.GroupsGenerator;
import com.foxminded.chendev.schoolconsoleapp.generator.datagenegator.StudentsCoursesRelationsGenerator;
import com.foxminded.chendev.schoolconsoleapp.generator.datagenegator.StudentsGenerator;

public class TestDataGenerator {

    private final StudentsGenerator studentsGenerator;
    private final GroupsGenerator groupsGenerator;
    private final CoursesGenerator coursesGenerator;
    private final StudentsCoursesRelationsGenerator studentsCoursesRelationsGenerator;

    public TestDataGenerator(StudentsGenerator studentsGenerator, GroupsGenerator groupsGenerator, CoursesGenerator coursesGenerator, StudentsCoursesRelationsGenerator studentsCoursesRelationsGenerator) {
        this.studentsGenerator = studentsGenerator;
        this.groupsGenerator = groupsGenerator;
        this.coursesGenerator = coursesGenerator;
        this.studentsCoursesRelationsGenerator = studentsCoursesRelationsGenerator;
    }

    public void generateTestData() {
        groupsGenerator.generateData();
        coursesGenerator.generateData();
        studentsGenerator.generateData();
        studentsCoursesRelationsGenerator.generateData();
    }
}
