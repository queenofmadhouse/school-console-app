package com.foxminded.chendev.schoolconsoleapp.initializer;

import com.foxminded.chendev.schoolconsoleapp.repository.CourseRepository;
import com.foxminded.chendev.schoolconsoleapp.repository.GroupRepository;
import com.foxminded.chendev.schoolconsoleapp.repository.StudentRepository;
import com.foxminded.chendev.schoolconsoleapp.service.impl.CourseDataGeneratorService;
import com.foxminded.chendev.schoolconsoleapp.service.impl.GroupDataGeneratorService;
import com.foxminded.chendev.schoolconsoleapp.service.impl.StudentCourseRelationDataGeneratorService;
import com.foxminded.chendev.schoolconsoleapp.service.impl.StudentDataGeneratorService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class ApplicationInitializerImpl implements ApplicationInitializer {


    private final StudentRepository studentRepository;
    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;
    private final StudentDataGeneratorService studentGenerator;
    private final GroupDataGeneratorService groupsGenerator;
    private final CourseDataGeneratorService courseGenerator;
    private final StudentCourseRelationDataGeneratorService studentCourseRelationGenerator;

    public ApplicationInitializerImpl(StudentRepository studentRepository, GroupRepository groupRepository, CourseRepository courseRepository,
                                      StudentDataGeneratorService studentGenerator,
                                      GroupDataGeneratorService groupsGenerator,
                                      CourseDataGeneratorService courseGenerator,
                                      StudentCourseRelationDataGeneratorService studentCourseRelationGenerator) {
        this.studentRepository = studentRepository;
        this.groupRepository = groupRepository;
        this.courseRepository = courseRepository;
        this.studentGenerator = studentGenerator;
        this.groupsGenerator = groupsGenerator;
        this.courseGenerator = courseGenerator;
        this.studentCourseRelationGenerator = studentCourseRelationGenerator;
    }

    @Override
    @PostConstruct
    public void init() {

        boolean isGroupsEmpty = isTableEmpty(groupRepository);
        boolean isStudentsEmpty = isTableEmpty(studentRepository);
        boolean isCoursesEmpty = isTableEmpty(courseRepository);

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

    private boolean isTableEmpty(JpaRepository dao) {
        List<?> list = dao.findAll();
        return list.isEmpty();
    }
}
