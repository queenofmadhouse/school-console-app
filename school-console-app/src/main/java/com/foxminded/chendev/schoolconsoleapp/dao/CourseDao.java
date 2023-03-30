package com.foxminded.chendev.schoolconsoleapp.dao;

import java.util.List;
import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;

public interface CourseDao extends CrudDao<Course, Long> {

    Course findCourseByCourseName(String courseName);
    void addStudentToCourse(Student student, long courseID);
    void removeStudentFromCourse(long studentID, long courseID);
    List<Student> findAllStudentsByCourseName(String name);
}
