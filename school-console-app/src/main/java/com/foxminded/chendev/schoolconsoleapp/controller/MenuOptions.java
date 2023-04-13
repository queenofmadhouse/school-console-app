package com.foxminded.chendev.schoolconsoleapp.controller;

import com.foxminded.chendev.schoolconsoleapp.controller.validator.Validator;
import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.service.CourseService;
import com.foxminded.chendev.schoolconsoleapp.service.GroupService;
import com.foxminded.chendev.schoolconsoleapp.service.StudentService;
import com.foxminded.chendev.schoolconsoleapp.view.ConsoleHandler;

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

            List<Student> studentList = courseService.findAllStudentsByCourseName(courseName);

            for (Student student : studentList) {
                consoleHandler.printMessage(student.toString());
            }
        }
    },
    ADD_NEW_STUDENT("c", "Add a new student") {
        public void execute(StudentService studentService, GroupService groupService, CourseService courseService,
                            Validator validator, ConsoleHandler consoleHandler) {

            consoleHandler.printMessage("Enter student name: ");

            String studentName = consoleHandler.readUserInputString();
            validator.validate(studentName);

            consoleHandler.printMessage("Enter student surname: ");

            String studentSurname = consoleHandler.readUserInputString();
            validator.validate(studentSurname);

            studentService.save(Student.builder().withFirstName(studentName).withLastName(studentSurname).build());
        }
    },
    DELETE_STUDENT_BY_ID("d", "Delete a student by the STUDENT_ID") {
        @Override
        public void execute(StudentService studentService, GroupService groupService, CourseService courseService,
                            Validator validator, ConsoleHandler consoleHandler) {

            consoleHandler.printMessage("Enter student ID: ");

            long studentID = Long.parseLong(consoleHandler.readUserInputString());
            validator.validate(studentID);

            studentService.deleteByID(studentID);
        }
    },
    ADD_STUDENT_TO_COURSE("e", "Add a student to the course (from a list)") {
        @Override
        public void execute(StudentService studentService, GroupService groupService, CourseService courseService,
                            Validator validator, ConsoleHandler consoleHandler) {

            consoleHandler.printMessage("Enter student ID: ");

            long studentID = consoleHandler.readUserInputNumber();
            validator.validate(studentID);

            List<Course> courses = courseService.findAll();

            consoleHandler.printMessage("Available courses: ");

            for (Course course : courses) {
                consoleHandler.printMessage(course.toString());
            }

            consoleHandler.printMessage("\nEnter course ID: ");

            long courseID = consoleHandler.readUserInputNumber();
            validator.validate(courseID);

            Student student = studentService.findById(studentID).orElse(null);

            courseService.addStudentToCourse(student, courseID);
        }
    },
    REMOVE_STUDENT_FROM_COURSE("f", "Remove the student from one of their courses") {
        @Override
        public void execute(StudentService studentService, GroupService groupService, CourseService courseService,
                            Validator validator, ConsoleHandler consoleHandler) {

            consoleHandler.printMessage("Enter student ID: ");

            long studentID = consoleHandler.readUserInputNumber();
            validator.validate(studentID);

            consoleHandler.printMessage("Enter course ID: ");

            long courseID = consoleHandler.readUserInputNumber();
            validator.validate(courseID);

            courseService.removeStudentFromCourse(studentID, courseID);
        }
    };

    private final String code;
    private final String description;

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
