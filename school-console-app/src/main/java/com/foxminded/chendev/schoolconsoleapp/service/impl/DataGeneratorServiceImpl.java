package com.foxminded.chendev.schoolconsoleapp.service.impl;

import com.foxminded.chendev.schoolconsoleapp.datagenerator.DataGenerator;
import com.foxminded.chendev.schoolconsoleapp.service.DataGeneratorService;
import org.springframework.stereotype.Service;

@Service
public class DataGeneratorServiceImpl implements DataGeneratorService {

    private final DataGenerator studentDataGenerator;
    private final DataGenerator groupDataGenerator;
    private final DataGenerator courseDataGenerator;

    public DataGeneratorServiceImpl(DataGenerator studentDataGenerator, DataGenerator groupDataGenerator,
                                    DataGenerator courseDataGenerator) {

        this.studentDataGenerator = studentDataGenerator;
        this.groupDataGenerator = groupDataGenerator;
        this.courseDataGenerator = courseDataGenerator;
    }

    @Override
    public void generateData() {

        groupDataGenerator.generateData();
        studentDataGenerator.generateData();
        courseDataGenerator.generateData();
    }
}
