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
}

