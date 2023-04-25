package com.foxminded.chendev.schoolconsoleapp.service.impl;

import com.foxminded.chendev.schoolconsoleapp.datagenerator.impl.StudentCourseRelationDataGenerator;
import com.foxminded.chendev.schoolconsoleapp.service.DataGeneratorService;
import org.springframework.stereotype.Service;

@Service
public class StudentCourseRelationDataGeneratorService implements DataGeneratorService {

    private final StudentCourseRelationDataGenerator studentCourseRelationDataGenerator;

    public StudentCourseRelationDataGeneratorService(StudentCourseRelationDataGenerator studentCourseRelationDataGenerator) {
        this.studentCourseRelationDataGenerator = studentCourseRelationDataGenerator;
    }

    @Override
    public void generateData() {
        studentCourseRelationDataGenerator.generateData();
    }
}
