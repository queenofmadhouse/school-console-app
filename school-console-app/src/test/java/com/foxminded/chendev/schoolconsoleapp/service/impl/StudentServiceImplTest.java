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

        int studentId = 1;

        doNothing().when(studentDao).deleteById(studentId);

        studentService.deleteById(studentId);

        verify(studentDao).deleteById(studentId);
    }

    @Test
    void saveShouldSaveObjectInDataBase() {

        Student studentAlex = Student.builder()
                .withFirstName("Alex")
                .withLastName("Kapranos")
                .withGroupId(1)
                .build();

        doNothing().when(studentDao).save(studentAlex);

        studentService.save(studentAlex);

        verify(studentDao).save(studentAlex);
    }

    @Test
    void addStudentToCourseShouldAddStudentToCourseWhenInputValid() {

        int studentId = 1;
        int courseId = 1;

        doNothing().when(studentDao).addStudentToCourse(studentId, courseId);

        studentService.addStudentToCourse(studentId, courseId);

        verify(studentDao).addStudentToCourse(studentId, courseId);
    }

    @Test
    void removeStudentFromCourseShouldRemoveAllRelationsById() {

        int studentId = 1;
        int courseId = 2;

        doNothing().when(studentDao).removeStudentFromCourse(studentId, courseId);

        studentService.removeStudentFromCourse(studentId, courseId);

        verify(studentDao).removeStudentFromCourse(studentId, courseId);
    }

    @Test
    void findAllStudentsByCourseNameShouldReturnListOfStudentsFoundByCourseName() {

        Student studentJoan = Student.builder()
                .withFirstName("Joan")
                .withLastName("Roberts")
                .withGroupId(5)
                .build();

        Student studentFillip = Student.builder()
                .withFirstName("Fillip")
                .withLastName("Some")
                .withGroupId(5)
                .build();

        Student studentJane = Student.builder()
                .withFirstName("Jane")
                .withLastName("Potters")
                .withGroupId(5)
                .build();

        String courseName = "Math";
        int courseId = 1;

        List<Student> studentList = new ArrayList<>();

        studentList.add(studentJoan);
        studentList.add(studentFillip);
        studentList.add(studentJane);

        Optional<Course> optionalCourse = Optional.ofNullable(Course.builder().withCourseId(courseId).build());

        when(courseDao.findCourseByName(courseName)).thenReturn(optionalCourse);
        when(studentDao.findStudentsByCourseId(courseId)).thenReturn(studentList);

        List<Student> foundStudentList = studentService.findAllStudentsByCourseName(courseName);

        assertFalse(foundStudentList.isEmpty());
        assertEquals(3, foundStudentList.size());

        verify(courseDao).findCourseByName(courseName);
        verify(studentDao).findStudentsByCourseId(courseId);
    }

    @Test
    void findAllStudentsByCourseNameShouldReturnEmptyListIfNotFoundByCourseName() {

        Optional<Course> emptyCourse = Optional.empty();
        String courseName = "Math";

        when(courseDao.findCourseByName(courseName)).thenReturn(emptyCourse);

        List<Student> emptyStudentList = studentService.findAllStudentsByCourseName(courseName);

        assertTrue(emptyStudentList.isEmpty());

        verify(courseDao).findCourseByName(courseName);
    }

    @Test
    void findByIdShouldReturnOptionalOfStudent() {

        Optional<Student> optionalStudent = Optional.ofNullable(Student.builder().build());
        int studentId = 1;

        when(studentDao.findById(studentId)).thenReturn(optionalStudent);

        studentService.findById(studentId);

        verify(studentDao).findById(studentId);
    }
}
