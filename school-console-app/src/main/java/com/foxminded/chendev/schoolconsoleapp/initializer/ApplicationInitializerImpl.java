package com.foxminded.chendev.schoolconsoleapp.initializer;

import com.foxminded.chendev.schoolconsoleapp.dao.CourseDao;
import com.foxminded.chendev.schoolconsoleapp.dao.GroupDao;
import com.foxminded.chendev.schoolconsoleapp.dao.StudentDao;
import com.foxminded.chendev.schoolconsoleapp.dao.impl.StudentDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.service.DataGeneratorService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class ApplicationInitializerImpl implements ApplicationInitializer {

    private final StudentDao studentDao;
    private final GroupDao groupDao;
    private final CourseDao courseDao;
    private final DataGeneratorService dataGeneratorService;

    public ApplicationInitializerImpl(StudentDao studentDao, GroupDao groupDao,
                                      CourseDao courseDao, DataGeneratorService dataGeneratorService) {
        this.studentDao = studentDao;
        this.groupDao = groupDao;
        this.courseDao = courseDao;
        this.dataGeneratorService = dataGeneratorService;
    }

    @Override
    @PostConstruct
    public void init() {
        dataGeneratorService.generateData();
    }
}
