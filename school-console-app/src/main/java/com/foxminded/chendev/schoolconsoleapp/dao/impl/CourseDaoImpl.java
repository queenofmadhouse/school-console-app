package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.CourseDao;
import com.foxminded.chendev.schoolconsoleapp.dao.DBConnector;
import com.foxminded.chendev.schoolconsoleapp.dao.DataBaseRuntimeException;
import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.entity.StudentCourseRelation;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseDaoImpl extends AbstractCrudDao<Course> implements CourseDao {

    private static final String INSERT_COURSE = "INSERT INTO school.courses (course_name, course_description) " +
            "VALUES (?, ?)";
    private static final String SELECT_COURSE_BY_ID = "SELECT * FROM school.courses WHERE course_id = ?";
    private static final String SELECT_ALL_COURSES = "SELECT * FROM school.courses";
    private static final String UPDATE_COURSE = "UPDATE school.courses SET course_name = ?" +
            ", course_description = ? WHERE course_id = ?";
    private static final String DELETE_COURSE_BY_ID = "DELETE FROM school.courses WHERE course_id = ?";
    private static final String SELECT_COURSE_BY_NAME = "SELECT * FROM school.courses WHERE course_name = ?";
    private final DBConnector connector;
    private final StudentCourseRelationDaoImpl studentCourseRelationDao;

    public CourseDaoImpl(DBConnector connector) {
        super(connector, INSERT_COURSE, SELECT_COURSE_BY_ID, SELECT_ALL_COURSES, UPDATE_COURSE, DELETE_COURSE_BY_ID);
        this.connector = connector;
        this.studentCourseRelationDao = new StudentCourseRelationDaoImpl(connector);
    }

    @Override
    protected Course mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Course.builder()
                .withCourseID(resultSet.getLong("course_id"))
                .withCourseName(resultSet.getString("course_name"))
                .withCourseDescription(resultSet.getString("course_description"))
                .build();
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, Course entity) throws SQLException {
        preparedStatement.setString(1, entity.getCourseName());
        preparedStatement.setString(2, entity.getCourseDescription());
    }

    @Override
    protected void updateValues(PreparedStatement preparedStatement, Course entity) throws SQLException {
        preparedStatement.setString(1, entity.getCourseName());
        preparedStatement.setString(2, entity.getCourseDescription());
        preparedStatement.setLong(3, entity.getCourseID());
    }

    @Override
    public Course findCourseByCourseName(String courseName) {
        Course findedCourse = super.findByStringParam(courseName, SELECT_COURSE_BY_NAME).orElseThrow(DataBaseRuntimeException::new);

        return findedCourse;
    }

    @Override
    public void addStudentToCourse(Student student, long courseID) {
        StudentCourseRelation studentCourseRelation = StudentCourseRelation.builder()
                .withStudentID(student.getStudentID())
                .withCourseID(courseID)
                .build();

        studentCourseRelationDao.saveRelation(studentCourseRelation);
    }

    @Override
    public void removeStudentFromCourse(long studentID, long courseID) {
        studentCourseRelationDao.deleteRelationByStudentID(studentID, courseID);
    }

    @Override
    public List<Student> findAllStudentsByCourseName(String courseName) {

        List<Student> students = new ArrayList<>();
        long courseID = findCourseByCourseName(courseName).getCourseID();
        List<StudentCourseRelation> studentCourseRelation = studentCourseRelationDao.findStudentsByCourseID(courseID);

        StudentDaoImpl studentDao = new StudentDaoImpl(connector);

        for (int i = 0; i < studentCourseRelation.size(); i++) {
            Student tempStudent = studentDao.findById(studentCourseRelation.get(i).getStudentID()).orElseThrow(DataBaseRuntimeException::new);
            students.add(tempStudent);
        }

        return students;
    }

    @Override
    public void deleteByID(long ID) {
        super.deleteByID(ID);
        studentCourseRelationDao.deleteAllRelationsByCourseID(ID);
    }
}
