package com.foxminded.chendev.schoolconsoleapp.initializer;

import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.repository.CourseRepository;
import com.foxminded.chendev.schoolconsoleapp.repository.GroupRepository;
import com.foxminded.chendev.schoolconsoleapp.repository.StudentRepository;
import com.foxminded.chendev.schoolconsoleapp.service.impl.CourseDataGeneratorService;
import com.foxminded.chendev.schoolconsoleapp.service.impl.GroupDataGeneratorService;
import com.foxminded.chendev.schoolconsoleapp.service.impl.StudentCourseRelationDataGeneratorService;
import com.foxminded.chendev.schoolconsoleapp.service.impl.StudentDataGeneratorService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ApplicationInitializerImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private GroupRepository groupRepository;

    @Mock
    private CourseRepository courseRepository;

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

        when(groupRepository.findAll()).thenReturn(Collections.emptyList());
        when(studentRepository.findAll()).thenReturn(Collections.emptyList());
        when(courseRepository.findAll()).thenReturn(Collections.emptyList());

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

        when(groupRepository.findAll()).thenReturn(notEmptyGroupList);
        when(studentRepository.findAll()).thenReturn(Collections.emptyList());
        when(courseRepository.findAll()).thenReturn(Collections.emptyList());

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

        when(groupRepository.findAll()).thenReturn(Collections.emptyList());
        when(studentRepository.findAll()).thenReturn(Collections.emptyList());
        when(courseRepository.findAll()).thenReturn(notEmptyCourseList);

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

        when(groupRepository.findAll()).thenReturn(Collections.emptyList());
        when(studentRepository.findAll()).thenReturn(notEmptyStudentList);
        when(courseRepository.findAll()).thenReturn(Collections.emptyList());

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

        when(groupRepository.findAll()).thenReturn(notEmptyGroupList);
        when(studentRepository.findAll()).thenReturn(notEmptyStudentList);
        when(courseRepository.findAll()).thenReturn(notEmptyCourseList);

        applicationInitializer.init();

        verify(groupsGenerator, never()).generateData();
        verify(studentGenerator, never()).generateData();
        verify(courseGenerator, never()).generateData();
        verify(studentCourseRelationGenerator, never()).generateData();
    }
}
