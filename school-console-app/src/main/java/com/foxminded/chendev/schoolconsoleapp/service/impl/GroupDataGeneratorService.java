package com.foxminded.chendev.schoolconsoleapp.service.impl;

import com.foxminded.chendev.schoolconsoleapp.datagenerator.DataGenerator;
import com.foxminded.chendev.schoolconsoleapp.datagenerator.impl.GroupDataGenerator;
import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import com.foxminded.chendev.schoolconsoleapp.service.DataGeneratorService;
import org.springframework.stereotype.Service;

@Service
public class GroupDataGeneratorService implements DataGeneratorService {

    private final GroupDataGenerator groupDataGenerator;

    public GroupDataGeneratorService(GroupDataGenerator groupDataGenerator) {
        this.groupDataGenerator = groupDataGenerator;
    }

    @Override
    public void generateData() {
        groupDataGenerator.generateData();
    }
}
