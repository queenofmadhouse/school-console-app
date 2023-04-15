package com.foxminded.chendev.schoolconsoleapp;

import com.foxminded.chendev.schoolconsoleapp.controller.MenuController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ApplicationRunner {

    public static void main(String[] args) {

        SpringApplication app = new SpringApplication(ApplicationRunner.class);
        app.setWebApplicationType(WebApplicationType.NONE);

        ApplicationContext applicationContext = SpringApplication.run(ApplicationRunner.class, args);

        MenuController menuController = applicationContext.getBean(MenuController.class);

        menuController.provideMenu();
    }
}
