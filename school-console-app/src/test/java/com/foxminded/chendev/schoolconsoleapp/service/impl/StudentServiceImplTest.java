package com.foxminded.chendev.schoolconsoleapp.service.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.CourseDao;
import com.foxminded.chendev.schoolconsoleapp.dao.StudentDao;
import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class StudentServiceImplTest {

    @MockBean
    CourseDao courseDao;

    @MockBean
    StudentDao studentDao;

    @Autowired
    StudentServiceImpl studentService;

    @Test
    void deleteByIDShouldDeleteAllRelations() {

        doNothing().when(studentDao).deleteById(1);

        studentService.deleteById(1);

        verify(studentDao).deleteById(1);
    }

    @Test
    void saveShouldSaveObjectInDataBase() {

        Student student = Student.builder()
                .withFirstName("Alex")
                .withLastName("Kapranos")
                .withGroupId(1)
                .build();

        doNothing().when(studentDao).save(student);

        studentService.save(student);

        verify(studentDao).save(student);
    }

    @Test
    void addStudentToCourseShouldAddStudentToCourseWhenInputValid() {

        doNothing().when(studentDao).addStudentToCourse(1, 1);

        studentService.addStudentToCourse(1, 1);

        verify(studentDao).addStudentToCourse(1, 1);
    }

    @Test
    void removeStudentFromCourseShouldRemoveAllRelationsById() {

        doNothing().when(studentDao).removeStudentFromCourse(1, 2);

        studentService.removeStudentFromCourse(1, 2);

        verify(studentDao).removeStudentFromCourse(1, 2);
    }

    @Test
    void findAllStudentsByCourseNameShouldReturnListOfStudentsFoundByCourseName() {

        Student student1 = Student.builder()
                .withFirstName("Joan")
                .withLastName("Roberts")
                .withGroupId(5)
                .build();

        Student student2 = Student.builder()
                .withFirstName("Fillip")
                .withLastName("Some")
                .withGroupId(5)
                .build();

        Student student3 = Student.builder()
                .withFirstName("Jane")
                .withLastName("Potters")
                .withGroupId(5)
                .build();

        List<Student> studentList = new ArrayList<>();

        studentList.add(student1);
        studentList.add(student2);
        studentList.add(student3);

        Optional<Course> course = Optional.ofNullable(Course.builder().withCourseId(1).build());

        when(courseDao.findCourseByName("Math")).thenReturn(course);
        when(studentDao.findStudentsByCourseId(1)).thenReturn(studentList);

        List<Student> studentList1 = studentService.findAllStudentsByCourseName("Math");

        assertFalse(studentList1.isEmpty());
        assertEquals(3, studentList1.size());

        verify(courseDao).findCourseByName("Math");
        verify(studentDao).findStudentsByCourseId(1);
    }

    @Test
    void findAllStudentsByCourseNameShouldReturnEmptyListIfNotFoundByCourseName() {

        Optional<Course> course = Optional.empty();

        when(courseDao.findCourseByName("Math")).thenReturn(course);

        List<Student> studentList1 = studentService.findAllStudentsByCourseName("Math");

        assertTrue(studentList1.isEmpty());

        verify(courseDao).findCourseByName("Math");
    }

    @Test
    void findByIdShouldReturnOptionalOfStudent() {

        Optional<Student> student = Optional.ofNullable(Student.builder().build());

        when(studentDao.findById(1)).thenReturn(student);

        studentService.findById(1);

        verify(studentDao).findById(1);
    }
}
