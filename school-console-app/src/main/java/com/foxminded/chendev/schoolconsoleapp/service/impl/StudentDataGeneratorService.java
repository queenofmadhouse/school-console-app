package com.foxminded.chendev.schoolconsoleapp.service.impl;

import com.foxminded.chendev.schoolconsoleapp.datagenerator.impl.StudentDataGenerator;
import com.foxminded.chendev.schoolconsoleapp.service.DataGeneratorService;
import org.springframework.stereotype.Service;

@Service
public class StudentDataGeneratorService implements DataGeneratorService {

    private final StudentDataGenerator studentDataGenerator;

    public StudentDataGeneratorService(StudentDataGenerator studentDataGenerator) {
        this.studentDataGenerator = studentDataGenerator;
    }

    @Override
    public void generateData() {
        studentDataGenerator.generateData();
    }
}
