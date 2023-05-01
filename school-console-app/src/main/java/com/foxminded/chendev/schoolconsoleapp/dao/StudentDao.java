package com.foxminded.chendev.schoolconsoleapp.dao;

import com.foxminded.chendev.schoolconsoleapp.entity.Student;

import java.util.List;

public interface StudentDao extends CrudDao<Student> {

    void removeStudentFromCourse(long studentId, long courseId);

    void addStudentToCourse(long studentId, long courseId);

    List<Student> findStudentsByCourseId(long courseId);

    void deleteAllRelationsByStudentId(long studentId);
}
