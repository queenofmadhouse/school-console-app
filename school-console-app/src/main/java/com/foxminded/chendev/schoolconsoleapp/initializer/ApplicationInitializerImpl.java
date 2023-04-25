package com.foxminded.chendev.schoolconsoleapp.initializer;

import com.foxminded.chendev.schoolconsoleapp.dao.CourseDao;
import com.foxminded.chendev.schoolconsoleapp.dao.CrudDao;
import com.foxminded.chendev.schoolconsoleapp.dao.GroupDao;
import com.foxminded.chendev.schoolconsoleapp.dao.StudentDao;
import com.foxminded.chendev.schoolconsoleapp.service.impl.CourseDataGeneratorService;
import com.foxminded.chendev.schoolconsoleapp.service.impl.GroupDataGeneratorService;
import com.foxminded.chendev.schoolconsoleapp.service.impl.StudentCourseRelationDataGeneratorService;
import com.foxminded.chendev.schoolconsoleapp.service.impl.StudentDataGeneratorService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class ApplicationInitializerImpl implements ApplicationInitializer {


    private final StudentDao studentDao;
    private final GroupDao groupDao;
    private final CourseDao courseDao;
    private final StudentDataGeneratorService studentGenerator;
    private final GroupDataGeneratorService groupsGenerator;
    private final CourseDataGeneratorService courseGenerator;
    private final StudentCourseRelationDataGeneratorService studentCourseRelationGenerator;

    public ApplicationInitializerImpl(StudentDao studentDao, GroupDao groupDao, CourseDao courseDao,
                                      StudentDataGeneratorService studentGenerator,
                                      GroupDataGeneratorService groupsGenerator,
                                      CourseDataGeneratorService courseGenerator,
                                      StudentCourseRelationDataGeneratorService studentCourseRelationGenerator) {
        this.studentDao = studentDao;
        this.groupDao = groupDao;
        this.courseDao = courseDao;
        this.studentGenerator = studentGenerator;
        this.groupsGenerator = groupsGenerator;
        this.courseGenerator = courseGenerator;
        this.studentCourseRelationGenerator = studentCourseRelationGenerator;
    }

    @Override
    @PostConstruct
    public void init() {

        boolean isGroupsEmpty = isTableEmpty(groupDao);
        boolean isStudentsEmpty = isTableEmpty(studentDao);
        boolean isCoursesEmpty = isTableEmpty(courseDao);

        if (isGroupsEmpty) {
            groupsGenerator.generateData();
        }
        if (isStudentsEmpty) {
            studentGenerator.generateData();
        }
        if (isCoursesEmpty) {
            courseGenerator.generateData();
        }
        if (isStudentsEmpty || isCoursesEmpty) {
            studentCourseRelationGenerator.generateData();
        }
    }

    private boolean isTableEmpty(CrudDao<?> dao) {
        List<?> list = dao.findAll();
        return list.isEmpty();
    }
}
