package com.foxminded.chendev.schoolconsoleapp.service;

import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface StudentService {

    void save(Student entity);

    Optional<Student> findById(long id);

    void deleteById(long id);

    void addStudentToCourse(long studentId, long courseId);

    void removeStudentFromCourse(long studentId, long courseId);

    @Transactional
    List<Student> findAllStudentsByCourseName(String courseName);
}
