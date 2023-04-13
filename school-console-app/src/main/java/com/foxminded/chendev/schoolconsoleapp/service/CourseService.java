package com.foxminded.chendev.schoolconsoleapp.service;

import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;

import java.util.List;

public interface CourseService {

    List<Student> findAllStudentsByCourseName(String courseName);

    List<Course> findAllCourses();
}
