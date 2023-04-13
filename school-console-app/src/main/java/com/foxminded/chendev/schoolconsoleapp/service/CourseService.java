package com.foxminded.chendev.schoolconsoleapp.service;

import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;

import java.util.List;

public interface CourseService {

    void addStudentToCourse(Student student, long courseID); //

    void removeStudentFromCourse(long studentID, long courseID); //

    List<Student> findAllStudentsByCourseName(String courseName); //

    List<Course> findAll();

}
