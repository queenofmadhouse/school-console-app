package com.foxminded.chendev.schoolconsoleapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Scanner;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }

    @Bean
    public int amountOfStudents() {
        return 200;
    }

    @Bean
    public int amountOfGroups() {
        return 10;
    }

    @Bean
    public int amountOfCourses() {
        return 10;
    }

//    @Bean
//    public Logger studentLogger() {
//        return LoggerFactory.getLogger(StudentDaoImpl.class);
//    }
//
//    @Bean
//    public Logger courseLogger() {
//        return LoggerFactory.getLogger(CourseDaoImpl.class);
//    }
//
//    @Bean
//    public Logger groupLogger() {
//        return LoggerFactory.getLogger(GroupDaoImpl.class);
//    }
//
//    @Bean
//    public Logger menuOptionsLogger() {
//        return LoggerFactory.getLogger(MenuOptions.class);
//    }
//
//    @Bean
//    public Logger menuControllerLogger() {
//        return LoggerFactory.getLogger(MenuController.class);
//    }
}

