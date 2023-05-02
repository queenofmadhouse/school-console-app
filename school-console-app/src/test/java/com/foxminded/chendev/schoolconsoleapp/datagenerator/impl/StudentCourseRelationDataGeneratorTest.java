package com.foxminded.chendev.schoolconsoleapp.datagenerator.impl;

import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.repository.CourseRepository;
import com.foxminded.chendev.schoolconsoleapp.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentCourseRelationDataGeneratorTest {

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private CourseRepository courseRepository;
    @InjectMocks
    private StudentCourseRelationDataGenerator generator;

    private List<Student> students;
    private List<Course> courses;

    @BeforeEach
    void setUp() {

        students = Arrays.asList(
                Student.builder()
                        .withUserId(1)
                        .withFirstName("John")
                        .withLastName("Doe")
                        .build(),
                Student.builder()
                        .withUserId(2)
                        .withFirstName("Jane")
                        .withLastName("Doe")
                        .build()
        );

        courses = Arrays.asList(
                Course.builder()
                        .withCourseId(1)
                        .withCourseName("Math")
                        .build(),
                Course.builder()
                        .withCourseId(2)
                        .withCourseName("Physics")
                        .build(),
                Course.builder()
                        .withCourseId(3)
                        .withCourseName("Chemistry")
                        .build()
        );
    }

    @Test
    void shouldGenerateData() {

        when(studentRepository.findAll()).thenReturn(students);
        when(courseRepository.findAll()).thenReturn(courses);

        generator.generateData();

        verify(studentRepository, times(1)).findAll();
        verify(courseRepository, times(1)).findAll();
        verify(studentRepository, atLeast(1)).addStudentToCourse(anyLong(), anyLong());
    }
}
