package com.foxminded.chendev.schoolconsoleapp.service.impl;

import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.exception.DataBaseRuntimeException;
import com.foxminded.chendev.schoolconsoleapp.repository.CourseRepository;
import com.foxminded.chendev.schoolconsoleapp.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class StudentServiceImplTest {

    @MockBean
    private CourseRepository courseRepository;

    @MockBean
    private StudentRepository studentRepository;

    @Autowired
    private StudentServiceImpl studentService;

    @Test
    void deleteByIDShouldDeleteAllRelations() {

        int studentId = 1;

        doNothing().when(studentRepository).deleteByUserId(studentId);

        studentService.deleteById(studentId);

        verify(studentRepository).deleteByUserId(studentId);
    }

    @Test
    void saveShouldSaveObjectInDataBase() {

        Student studentAlex = Student.builder()
                .withFirstName("Alex")
                .withLastName("Kapranos")
                .withGroupId(1)
                .build();

        when(studentRepository.save(studentAlex)).thenReturn(studentAlex);

        studentService.save(studentAlex);

        verify(studentRepository).save(studentAlex);
    }

    @Test
    void addStudentToCourseShouldAddStudentToCourseWhenInputValid() {

        int studentId = 1;
        int courseId = 1;

        doNothing().when(studentRepository).addStudentToCourse(studentId, courseId);

        studentService.addStudentToCourse(studentId, courseId);

        verify(studentRepository).addStudentToCourse(studentId, courseId);
    }

    @Test
    void removeStudentFromCourseShouldRemoveAllRelationsById() {

        int studentId = 1;
        int courseId = 2;

        doNothing().when(studentRepository).removeStudentFromCourse(studentId, courseId);

        studentService.removeStudentFromCourse(studentId, courseId);

        verify(studentRepository).removeStudentFromCourse(studentId, courseId);
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

        when(courseRepository.findByCourseName(courseName)).thenReturn(optionalCourse);
        when(studentRepository.findStudentsByCourseId(courseId)).thenReturn(studentList);

        List<Student> foundStudentList = studentService.findAllStudentsByCourseName(courseName);

        assertFalse(foundStudentList.isEmpty());
        assertEquals(3, foundStudentList.size());

        verify(courseRepository).findByCourseName(courseName);
        verify(studentRepository).findStudentsByCourseId(courseId);
    }

    @Test
    void findAllStudentsByCourseNameShouldReturnEmptyListIfNotFoundByCourseName() {

        Optional<Course> emptyCourse = Optional.empty();
        String courseName = "Math";

        when(courseRepository.findByCourseName(courseName)).thenReturn(emptyCourse);

        List<Student> emptyStudentList = studentService.findAllStudentsByCourseName(courseName);

        assertTrue(emptyStudentList.isEmpty());

        verify(courseRepository).findByCourseName(courseName);
    }

    @Test
    void findByIdShouldReturnOptionalOfStudent() {

        Optional<Student> optionalStudent = Optional.ofNullable(Student.builder().build());
        int studentId = 1;

        when(studentRepository.findByUserId(studentId)).thenReturn(optionalStudent);

        studentService.findById(studentId);

        verify(studentRepository).findByUserId(studentId);
    }

    @Test
    void saveShouldThrowDataBaseRuntimeExceptionWhenExceptionOccurs() {

        Student studentJane = Student.builder()
                .withFirstName("Jane")
                .withLastName("Potters")
                .withGroupId(5)
                .build();

        doThrow(new RuntimeException()).when(studentRepository).save(studentJane);

        assertThrows(DataBaseRuntimeException.class, () -> studentService.save(studentJane));
    }

    @Test
    void findByIdShouldThrowDataBaseRuntimeExceptionWhenExceptionOccurs() {

        long studentId = 1;

        doThrow(new RuntimeException()).when(studentRepository).findByUserId(studentId);

        assertThrows(DataBaseRuntimeException.class, () -> studentService.findById(studentId));
    }

    @Test
    void deleteByIdShouldThrowDataBaseRuntimeExceptionWhenExceptionOccurs() {

        long studentId = 1;

        doThrow(new RuntimeException()).when(studentRepository).deleteByUserId(studentId);

        assertThrows(DataBaseRuntimeException.class, () -> studentService.deleteById(studentId));
    }

    @Test
    void addStudentToCourseShouldThrowDataBaseRuntimeExceptionWhenExceptionOccurs() {

        long studentId = 1;
        long courseId = 1;

        doThrow(new RuntimeException()).when(studentRepository).addStudentToCourse(studentId, courseId);

        assertThrows(DataBaseRuntimeException.class, () -> studentService.addStudentToCourse(studentId, courseId));
    }

    @Test
    void removeStudentFromCourseShouldThrowDataBaseRuntimeExceptionWhenExceptionOccurs() {

        long studentId = 1;
        long courseId = 1;

        doThrow(new RuntimeException()).when(studentRepository).removeStudentFromCourse(studentId, courseId);

        assertThrows(DataBaseRuntimeException.class, () -> studentService.removeStudentFromCourse(studentId, courseId));
    }

    @Test
    void findAllStudentsByCourseNameShouldThrowDataBaseRuntimeExceptionWhenExceptionOccurs() {

        String courseName = "Math";

        doThrow(new RuntimeException()).when(courseRepository).findByCourseName(courseName);

        assertThrows(DataBaseRuntimeException.class, () -> studentService.findAllStudentsByCourseName(courseName));
    }
}
