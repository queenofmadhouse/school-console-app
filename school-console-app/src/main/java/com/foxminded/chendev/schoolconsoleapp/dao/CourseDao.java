package com.foxminded.chendev.schoolconsoleapp.dao;

import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.entity.StudentCourseRelation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface CourseDao extends CrudDao<Course> {

    Course findCourseByCourseName(String courseName);

    void addStudentToCourse(Student student, long courseID);

    void removeStudentFromCourse(long studentID, long courseID);

    List<Student> findAllStudentsByCourseName(String name);

    void insertRelation(StudentCourseRelation entity) throws SQLException;

    void saveRelation(StudentCourseRelation studentCourseRelation);

    List<StudentCourseRelation> findCoursesByStudentID(long studentID);

    List<StudentCourseRelation> findStudentsByCourseID(long courseID);

    void deleteRelationByStudentID(long studentID, long courseID);

    void deleteAllRelationsByStudentID(long studentID);

    void deleteAllRelationsByCourseID(long courseID);
}
