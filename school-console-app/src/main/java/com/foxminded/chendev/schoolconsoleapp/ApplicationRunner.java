package com.foxminded.chendev.schoolconsoleapp;

import com.foxminded.chendev.schoolconsoleapp.controller.MenuController;
import com.foxminded.chendev.schoolconsoleapp.controller.validator.InputValidator;
import com.foxminded.chendev.schoolconsoleapp.controller.validator.Validator;
import com.foxminded.chendev.schoolconsoleapp.dao.CourseDao;
import com.foxminded.chendev.schoolconsoleapp.dao.DBConnector;
import com.foxminded.chendev.schoolconsoleapp.dao.GroupDao;
import com.foxminded.chendev.schoolconsoleapp.dao.StudentDao;
import com.foxminded.chendev.schoolconsoleapp.dao.impl.CourseDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.dao.impl.GroupDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.dao.impl.StudentDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.generator.TestDataGenerator;
import com.foxminded.chendev.schoolconsoleapp.generator.datagenegator.CoursesGenerator;
import com.foxminded.chendev.schoolconsoleapp.generator.datagenegator.GroupsGenerator;
import com.foxminded.chendev.schoolconsoleapp.generator.datagenegator.StudentsCoursesRelationsGenerator;
import com.foxminded.chendev.schoolconsoleapp.generator.datagenegator.StudentsGenerator;
import com.foxminded.chendev.schoolconsoleapp.generator.tablesgenerator.SchoolTablesGenerator;
import com.foxminded.chendev.schoolconsoleapp.generator.tablesgenerator.TableGenerator;
import com.foxminded.chendev.schoolconsoleapp.generator.tablesgenerator.TableGeneratorImpl;
import com.foxminded.chendev.schoolconsoleapp.reader.SQLFileReader;
import com.foxminded.chendev.schoolconsoleapp.view.ConsoleHandler;
import com.foxminded.chendev.schoolconsoleapp.view.ConsoleHandlerImpl;

import java.util.Scanner;

public class ApplicationRunner {

    public static void main(String[] args) {

        SQLFileReader sqlFileReader = new SQLFileReader();

        final String settingDataBasePath = "database";
        final String STUDENTS_CREATE_QUERY = sqlFileReader.readFile("students_create.sql");
        final String GROUPS_CREATE_QUERY = sqlFileReader.readFile("groups_create.sql");
        final String COURSES_CREATE_QUERY = sqlFileReader.readFile("courses_create.sql");
        final String STUDENTS_COURSES_RELATION_CREATE_QUERY = sqlFileReader.readFile("students_courses_relation.sql");
        final int amountOfStudents = 300;
        final int amountOfGroups = 10;
        final int amountOfCourses = 10;

        DBConnector connector = new DBConnector(settingDataBasePath);

        StudentDao studentDao = new StudentDaoImpl(connector);
        GroupDao groupDao = new GroupDaoImpl(connector);
        CourseDao courseDao = new CourseDaoImpl(connector);

        Validator validator = new InputValidator();
        ConsoleHandler consoleHandler = new ConsoleHandlerImpl(new Scanner(System.in));

        TableGenerator tableGenerator = new TableGeneratorImpl(connector);

        SchoolTablesGenerator schoolTablesGenerator = new SchoolTablesGenerator(tableGenerator, STUDENTS_CREATE_QUERY,
                GROUPS_CREATE_QUERY, COURSES_CREATE_QUERY, STUDENTS_COURSES_RELATION_CREATE_QUERY);

        StudentsGenerator studentsGenerator = new StudentsGenerator(studentDao, groupDao, amountOfStudents);
        GroupsGenerator groupsGenerator = new GroupsGenerator(groupDao, amountOfGroups);
        CoursesGenerator coursesGenerator = new CoursesGenerator(courseDao, amountOfCourses);
        StudentsCoursesRelationsGenerator studentsCoursesRelationsGenerator = new StudentsCoursesRelationsGenerator(
                studentDao, courseDao);

        TestDataGenerator testDataGenerator = new TestDataGenerator(studentsGenerator,
                groupsGenerator, coursesGenerator, studentsCoursesRelationsGenerator);

        MenuController menuController = new MenuController(studentDao, groupDao, courseDao,
                validator, consoleHandler);

        schoolTablesGenerator.createTables();

        testDataGenerator.generateTestData();

        menuController.provideMenu();
    }
}
