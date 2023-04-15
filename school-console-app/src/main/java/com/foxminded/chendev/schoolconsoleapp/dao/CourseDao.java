package com.foxminded.chendev.schoolconsoleapp.dao;

import com.foxminded.chendev.schoolconsoleapp.entity.Course;

import java.util.List;

public interface CourseDao {

    List<Course> findAllCourses();
    List<Course> findCoursesByStudentId(long studentId);

    void deleteAllRelationsByCourseId(long courseId);

    Course findCourseByCourseName(String courseName);
}
