package com.foxminded.chendev.schoolconsoleapp.configuration;

import com.foxminded.chendev.schoolconsoleapp.controller.MenuOptions;
import com.foxminded.chendev.schoolconsoleapp.dao.impl.CourseDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.dao.impl.GroupDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.dao.impl.StudentDaoImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Bean
    public Logger studentLogger() {
        return LoggerFactory.getLogger(StudentDaoImpl.class);
    }

    @Bean
    public Logger courseLogger() {
        return LoggerFactory.getLogger(CourseDaoImpl.class);
    }

    @Bean
    public Logger groupLogger() {
        return LoggerFactory.getLogger(GroupDaoImpl.class);
    }

    @Bean
    public Logger menuOptionLogger() {
        return LoggerFactory.getLogger(MenuOptions.class);
    }
}

