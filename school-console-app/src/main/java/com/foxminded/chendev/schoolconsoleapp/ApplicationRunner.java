package com.foxminded.chendev.schoolconsoleapp;

import com.foxminded.chendev.schoolconsoleapp.controller.MenuController;
import com.foxminded.chendev.schoolconsoleapp.controller.validator.InputValidator;
import com.foxminded.chendev.schoolconsoleapp.controller.validator.Validator;
import com.foxminded.chendev.schoolconsoleapp.dao.CourseDao;
import com.foxminded.chendev.schoolconsoleapp.dao.GroupDao;
import com.foxminded.chendev.schoolconsoleapp.dao.StudentDao;
import com.foxminded.chendev.schoolconsoleapp.dao.impl.CourseDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.dao.impl.GroupDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.dao.impl.StudentDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import com.foxminded.chendev.schoolconsoleapp.reader.SQLFileReader;
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

        StudentDao studentDao = applicationContext.getBean(StudentDaoImpl.class);
        GroupDao groupDao = applicationContext.getBean(GroupDaoImpl.class);
        CourseDao courseDao = applicationContext.getBean(CourseDaoImpl.class);

        Validator validator = new InputValidator();
        ConsoleHandler consoleHandler = new ConsoleHandlerImpl(new Scanner(System.in));

        MenuController menuController = new MenuController(studentDao, groupDao, courseDao,
                validator, consoleHandler);

        menuController.provideMenu();
    }
}
