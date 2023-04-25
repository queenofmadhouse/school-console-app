package com.foxminded.chendev.schoolconsoleapp.dao;

import com.foxminded.chendev.schoolconsoleapp.entity.Course;

import java.util.List;
import java.util.Optional;

public interface CourseDao extends CrudDao<Course> {

    List<Course> findAllCourses();
    List<Course> findCoursesByStudentId(long studentId);

    void deleteAllRelationsByCourseId(long courseId);

    Optional<Course> findCourseByName(String courseName);
}
