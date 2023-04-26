package com.foxminded.chendev.schoolconsoleapp.controller;

import com.foxminded.chendev.schoolconsoleapp.controller.validator.Validator;
import com.foxminded.chendev.schoolconsoleapp.service.CourseService;
import com.foxminded.chendev.schoolconsoleapp.service.GroupService;
import com.foxminded.chendev.schoolconsoleapp.service.StudentService;
import com.foxminded.chendev.schoolconsoleapp.view.ConsoleHandler;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MenuController {

    private final StudentService studentService;
    private final GroupService groupService;
    private final CourseService courseService;
    private final Validator validator;
    private final ConsoleHandler consoleHandler;
    private final Logger menuOptionsLogger;

    public MenuController(StudentService studentService, GroupService groupService, CourseService courseService,
                          Validator validator, ConsoleHandler consoleHandler, @Qualifier("menuOptionLogger") Logger menuOptionsLogger) {

        this.studentService = studentService;
        this.groupService = groupService;
        this.courseService = courseService;
        this.validator = validator;
        this.consoleHandler = consoleHandler;
        this.menuOptionsLogger = menuOptionsLogger;
    }

    public void provideMenu() {

        while (true) {
            try {

                provideMainMenu();

                consoleHandler.printMessage("Enter code: ");

                String code = consoleHandler.readUserInputString();
                validator.validate(code);

                if (code.equals("exit")) {
                    break;
                }

                findOption(code).execute(menuOptionsLogger, studentService, groupService, courseService, validator, consoleHandler);

                consoleHandler.printMessage("done!");
            } catch (IllegalArgumentException e) {
                consoleHandler.printMessage(e.getMessage());
            }
        }
    }


    private void provideMainMenu() {
        consoleHandler.printMessage("---School Console Application---");
        for (MenuOptions option : MenuOptions.values()) {
            consoleHandler.printMessage(option.getCode() + ". " + option.getDescription());
        }
    }

    protected MenuOptions findOption(String code) {
        for (MenuOptions option : MenuOptions.values()) {
            if (option.getCode().equals(code)) {
                return option;
            }
        }
        throw new IllegalArgumentException("Invalid code. Try again or type 'exit' to exit from application");
    }
}
