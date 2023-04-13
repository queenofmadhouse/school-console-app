package com.foxminded.chendev.schoolconsoleapp.dao;

import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.entity.StudentCourseRelation;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;

public interface CourseDao {

    void saveRelation(StudentCourseRelation studentCourseRelation);

    List<StudentCourseRelation> findCoursesByStudentID(long studentID);

    List<StudentCourseRelation> findStudentsByCourseID(long courseID);

    void deleteRelationByStudentID(long studentID, long courseID);

    void deleteAllRelationsByStudentID(long studentID);

    void deleteAllRelationsByCourseID(long courseID);

    RowMapper<StudentCourseRelation> studentCourseRelationRowMapper();

    Course findCourseByCourseName(String courseName);

    void addStudentToCourse(Student student, long courseID);

    void removeStudentFromCourse(long studentID, long courseID);
}
