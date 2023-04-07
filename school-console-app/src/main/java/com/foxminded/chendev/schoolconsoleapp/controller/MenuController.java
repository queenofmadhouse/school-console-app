package com.foxminded.chendev.schoolconsoleapp.controller;

import com.foxminded.chendev.schoolconsoleapp.controller.validator.Validator;
import com.foxminded.chendev.schoolconsoleapp.dao.CourseDao;
import com.foxminded.chendev.schoolconsoleapp.dao.GroupDao;
import com.foxminded.chendev.schoolconsoleapp.dao.StudentDao;
import com.foxminded.chendev.schoolconsoleapp.view.ConsoleHandler;

public class MenuController {

    private final StudentDao studentDao;
    private final GroupDao groupDao;
    private final CourseDao courseDao;
    private final Validator validator;
    private final ConsoleHandler consoleHandler;

    public MenuController(StudentDao studentDao, GroupDao groupDao, CourseDao courseDao,
                          Validator validator, ConsoleHandler consoleHandler) {

        this.studentDao = studentDao;
        this.groupDao = groupDao;
        this.courseDao = courseDao;
        this.validator = validator;
        this.consoleHandler = consoleHandler;
    }

    int i = 0;

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


                findOption(code).execute(studentDao, groupDao, courseDao, validator, consoleHandler);

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
