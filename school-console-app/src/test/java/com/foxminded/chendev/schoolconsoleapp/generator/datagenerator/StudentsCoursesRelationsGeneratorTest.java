package com.foxminded.chendev.schoolconsoleapp.generator.datagenerator;

import com.foxminded.chendev.schoolconsoleapp.dao.impl.CourseDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.dao.impl.StudentDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.entity.StudentCourseRelation;
import com.foxminded.chendev.schoolconsoleapp.generator.datagenegator.StudentsCoursesRelationsGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentsCoursesRelationsGeneratorTest {

    @Mock
    private StudentDaoImpl studentDao;
    @Mock
    private CourseDaoImpl courseDao;

    @InjectMocks
    private StudentsCoursesRelationsGenerator generator;

    private List<Student> students;
    private List<Course> courses;

    @BeforeEach
    void setUp() {

        students = Arrays.asList(
                Student.builder()
                        .withStudentId(1)
                        .withFirstName("John")
                        .withLastName("Doe")
                        .build(),
                Student.builder()
                        .withStudentId(2)
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

        when(studentDao.findAll()).thenReturn(students);
        when(courseDao.findAll()).thenReturn(courses);

        generator.generateData();

        verify(studentDao, times(1)).findAll();
        verify(courseDao, times(1)).findAll();
        verify(courseDao, atLeast(1)).saveRelation(any(StudentCourseRelation.class));
    }
}
