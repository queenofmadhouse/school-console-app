package com.foxminded.chendev.schoolconsoleapp.service.impl;

import com.foxminded.chendev.schoolconsoleapp.datagenerator.DataGenerator;
import com.foxminded.chendev.schoolconsoleapp.datagenerator.impl.CourseDataGenerator;
import com.foxminded.chendev.schoolconsoleapp.service.DataGeneratorService;
import org.springframework.stereotype.Service;

@Service
public class CourseDataGeneratorService implements DataGeneratorService {

    private final CourseDataGenerator courseDataGenerator;

    public CourseDataGeneratorService(CourseDataGenerator courseDataGenerator) {
        this.courseDataGenerator = courseDataGenerator;
    }

    @Override
    public void generateData() {
        courseDataGenerator.generateData();
    }
}
