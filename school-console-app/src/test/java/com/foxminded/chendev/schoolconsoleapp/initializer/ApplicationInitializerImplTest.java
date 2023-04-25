package com.foxminded.chendev.schoolconsoleapp.initializer;

import com.foxminded.chendev.schoolconsoleapp.dao.CourseDao;
import com.foxminded.chendev.schoolconsoleapp.dao.GroupDao;
import com.foxminded.chendev.schoolconsoleapp.dao.StudentDao;
import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.service.impl.CourseDataGeneratorService;
import com.foxminded.chendev.schoolconsoleapp.service.impl.GroupDataGeneratorService;
import com.foxminded.chendev.schoolconsoleapp.service.impl.StudentCourseRelationDataGeneratorService;
import com.foxminded.chendev.schoolconsoleapp.service.impl.StudentDataGeneratorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationInitializerImplTest {

    @Mock
    private StudentDao studentDao;
    @Mock
    private GroupDao groupDao;
    @Mock
    private CourseDao courseDao;
    @Mock
    private StudentDataGeneratorService studentGenerator;
    @Mock
    private GroupDataGeneratorService groupsGenerator;
    @Mock
    private CourseDataGeneratorService courseGenerator;
    @Mock
    private StudentCourseRelationDataGeneratorService studentCourseRelationGenerator;

    @InjectMocks
    private ApplicationInitializerImpl applicationInitializer;

    @Test
    void initShouldCallGeneratorsWhenTablesAreEmpty() {
        when(groupDao.findAll()).thenReturn(Collections.emptyList());
        when(studentDao.findAll()).thenReturn(Collections.emptyList());
        when(courseDao.findAll()).thenReturn(Collections.emptyList());

        applicationInitializer.init();

        verify(groupsGenerator).generateData();
        verify(studentGenerator).generateData();
        verify(courseGenerator).generateData();
        verify(studentCourseRelationGenerator).generateData();
    }

    @Test
    void initShouldCallStudentCourseRelationGeneratorWhenStudentsOrCoursesEmpty() {

        List<Group> notEmptyGroupList = new ArrayList<>();

        Group groupA1 = Group.builder()
                .withGroupName("A1")
                .build();

        notEmptyGroupList.add(groupA1);

        when(groupDao.findAll()).thenReturn(notEmptyGroupList);
        when(studentDao.findAll()).thenReturn(Collections.emptyList());
        when(courseDao.findAll()).thenReturn(Collections.emptyList());

        applicationInitializer.init();

        verify(groupsGenerator, never()).generateData();
        verify(studentGenerator).generateData();
        verify(courseGenerator).generateData();
        verify(studentCourseRelationGenerator).generateData();
    }

    @Test
    void initShouldCallStudentCourseRelationGeneratorWhenStudentsTableIsEmptyAndCoursesTableIsNotEmpty() {

        List<Course> notEmptyCourseList = new ArrayList<>();

        Course courseMath = Course.builder()
                .withCourseName("Math")
                .build();

        notEmptyCourseList.add(courseMath);

        when(groupDao.findAll()).thenReturn(Collections.emptyList());
        when(studentDao.findAll()).thenReturn(Collections.emptyList());
        when(courseDao.findAll()).thenReturn(notEmptyCourseList);

        applicationInitializer.init();

        verify(studentCourseRelationGenerator).generateData();
    }

    @Test
    void initShouldCallStudentCourseRelationGeneratorWhenStudentsTableIsNotEmptyAndCoursesTableIsEmpty() {

        List<Student> notEmptyStudentList = new ArrayList<>();

        Student studentJane = Student.builder()
                .withFirstName("Jane")
                .build();

        notEmptyStudentList.add(studentJane);

        when(groupDao.findAll()).thenReturn(Collections.emptyList());
        when(studentDao.findAll()).thenReturn(notEmptyStudentList);
        when(courseDao.findAll()).thenReturn(Collections.emptyList());

        applicationInitializer.init();

        verify(studentCourseRelationGenerator).generateData();
    }

    @Test
    void initShouldNotCallAnyGeneratorWhenTablesAreNotEmpty() {

        List<Group> notEmptyGroupList = new ArrayList<>();
        List<Student> notEmptyStudentList = new ArrayList<>();
        List<Course> notEmptyCourseList = new ArrayList<>();

        Group groupA1 = Group.builder()
                .withGroupName("A1")
                .build();

        Student studentJane = Student.builder()
                .withFirstName("Jane")
                .build();

        Course courseMath = Course.builder()
                .withCourseName("Math")
                .build();

        notEmptyGroupList.add(groupA1);
        notEmptyStudentList.add(studentJane);
        notEmptyCourseList.add(courseMath);

        when(groupDao.findAll()).thenReturn(notEmptyGroupList);
        when(studentDao.findAll()).thenReturn(notEmptyStudentList);
        when(courseDao.findAll()).thenReturn(notEmptyCourseList);

        applicationInitializer.init();

        verify(groupsGenerator, never()).generateData();
        verify(studentGenerator, never()).generateData();
        verify(courseGenerator, never()).generateData();
        verify(studentCourseRelationGenerator, never()).generateData();
    }
}
