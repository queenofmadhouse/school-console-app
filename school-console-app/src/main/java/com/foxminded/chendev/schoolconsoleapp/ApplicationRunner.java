package com.foxminded.chendev.schoolconsoleapp;

import com.foxminded.chendev.schoolconsoleapp.controller.MenuController;
import com.foxminded.chendev.schoolconsoleapp.controller.validator.InputValidator;
import com.foxminded.chendev.schoolconsoleapp.controller.validator.Validator;
import com.foxminded.chendev.schoolconsoleapp.service.GroupService;
import com.foxminded.chendev.schoolconsoleapp.service.StudentService;
import com.foxminded.chendev.schoolconsoleapp.service.impl.CourseServiceImpl;
import com.foxminded.chendev.schoolconsoleapp.service.impl.GroupServiceImpl;
import com.foxminded.chendev.schoolconsoleapp.service.impl.StudentServiceImpl;
import com.foxminded.chendev.schoolconsoleapp.view.ConsoleHandler;
import com.foxminded.chendev.schoolconsoleapp.view.ConsoleHandlerImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class ApplicationRunner {

    public static void main(String[] args) {


        SpringApplication app = new SpringApplication(ApplicationRunner.class);
        app.setWebApplicationType(WebApplicationType.NONE);

        ApplicationContext applicationContext = SpringApplication.run(ApplicationRunner.class, args);

        StudentService studentService = applicationContext.getBean(StudentServiceImpl.class);
        GroupService groupService = applicationContext.getBean(GroupServiceImpl.class);
        CourseServiceImpl courseService = applicationContext.getBean(CourseServiceImpl.class);

        Validator validator = new InputValidator();
        ConsoleHandler consoleHandler = new ConsoleHandlerImpl(new Scanner(System.in));

        MenuController menuController = new MenuController(studentService, groupService, courseService,
                validator, consoleHandler);

        menuController.provideMenu();

    }
}
