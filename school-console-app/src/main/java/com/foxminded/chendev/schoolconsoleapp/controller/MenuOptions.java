package com.foxminded.chendev.schoolconsoleapp.controller;

import com.foxminded.chendev.schoolconsoleapp.controller.validator.Validator;
import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.service.CourseService;
import com.foxminded.chendev.schoolconsoleapp.service.GroupService;
import com.foxminded.chendev.schoolconsoleapp.service.StudentService;
import com.foxminded.chendev.schoolconsoleapp.view.ConsoleHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public enum MenuOptions {

    FIND_GROUPS_WITH_LESS_OR_EQUAL_STUDENTS("a", "Find all groups with less or equal students’ number") {
        @Override
        public void execute(StudentService studentService, GroupService groupService, CourseService courseService,
                            Validator validator, ConsoleHandler consoleHandler) {

            consoleHandler.printMessage("Enter value: ");

            long value = consoleHandler.readUserInputNumber();
            validator.validate(value);

            List<Group> groupsList = groupService.findGroupsWithLessOrEqualStudents(value);

            logger.info("Finding groups with less or equal students, number: " + value);

            for (Group group : groupsList) {
                consoleHandler.printMessage(group.toString());
            }
        }
    },
    FIND_STUDENTS_BY_COURSE("b", "Find all students related to the course with the given name") {
        @Override
        public void execute(StudentService studentService, GroupService groupService, CourseService courseService,
                            Validator validator, ConsoleHandler consoleHandler) {

            consoleHandler.printMessage("Enter course name: ");

            String courseName = consoleHandler.readUserInputString();
            validator.validate(courseName);

            logger.info("Finding students by course name, name: " + courseName);

            List<Student> studentList = studentService.findAllStudentsByCourseName(courseName);

            for (Student student : studentList) {
                consoleHandler.printMessage(student.toString());
            }
        }
    },
    ADD_NEW_STUDENT("c", "Add a new student") {
        @Override
        public void execute(StudentService studentService, GroupService groupService, CourseService courseService,
                            Validator validator, ConsoleHandler consoleHandler) {

            consoleHandler.printMessage("Enter student name: ");

            String studentName = consoleHandler.readUserInputString();
            validator.validate(studentName);

            consoleHandler.printMessage("Enter student surname: ");

            String studentSurname = consoleHandler.readUserInputString();
            validator.validate(studentSurname);

            Student studentForAdding = Student.builder()
                    .withFirstName(studentName)
                    .withLastName(studentSurname)
                    .build();

            logger.info("Adding new student: " +studentForAdding);

            studentService.save(studentForAdding);
        }
    },
    DELETE_STUDENT_BY_ID("d", "Delete a student by the STUDENT_ID") {
        @Override
        public void execute(StudentService studentService, GroupService groupService, CourseService courseService,
                            Validator validator, ConsoleHandler consoleHandler) {

            consoleHandler.printMessage("Enter student ID: ");

            long studentId = Long.parseLong(consoleHandler.readUserInputString());
            validator.validate(studentId);

            logger.info("Deleting student by id: " + studentId);

            studentService.deleteById(studentId);
        }
    },
    ADD_STUDENT_TO_COURSE("e", "Add a student to the course (from a list)") {
        @Override
        public void execute(StudentService studentService, GroupService groupService, CourseService courseService,
                            Validator validator, ConsoleHandler consoleHandler) {

            consoleHandler.printMessage("Enter student ID: ");

            long studentId = consoleHandler.readUserInputNumber();
            validator.validate(studentId);

            List<Course> courses = courseService.findAllCourses();

            consoleHandler.printMessage("Available courses: ");

            for (Course course : courses) {
                consoleHandler.printMessage(course.toString());
            }

            consoleHandler.printMessage("\nEnter course ID: ");

            long courseId = consoleHandler.readUserInputNumber();
            validator.validate(courseId);

            logger.info("Adding student to course with studentId: " + studentId +
                    "courseId: " + courseId);

            studentService.addStudentToCourse(studentId, courseId);
        }
    },
    REMOVE_STUDENT_FROM_COURSE("f", "Remove the student from one of their courses") {
        @Override
        public void execute(StudentService studentService, GroupService groupService, CourseService courseService,
                            Validator validator, ConsoleHandler consoleHandler) {

            consoleHandler.printMessage("Enter student ID: ");

            long studentId = consoleHandler.readUserInputNumber();
            validator.validate(studentId);

            consoleHandler.printMessage("Enter course ID: ");

            long courseId = consoleHandler.readUserInputNumber();
            validator.validate(courseId);

            logger.info("Removing student from course with studentId: " + studentId +
                    "courseId: " + courseId);
            studentService.removeStudentFromCourse(studentId, courseId);
        }
    };

    private final String code;
    private final String description;
    private static final Logger logger = LoggerFactory.getLogger(MenuOptions.class);

    MenuOptions(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public abstract void execute(StudentService studentService, GroupService groupService, CourseService courseService,
                                 Validator validator, ConsoleHandler consoleHandler);

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
