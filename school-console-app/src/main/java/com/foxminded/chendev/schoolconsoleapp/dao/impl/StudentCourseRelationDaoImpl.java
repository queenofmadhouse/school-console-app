package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.DBConnector;
import com.foxminded.chendev.schoolconsoleapp.dao.DataBaseRuntimeException;
import com.foxminded.chendev.schoolconsoleapp.dao.StudentCourseRelationDao;
import com.foxminded.chendev.schoolconsoleapp.entity.StudentCourseRelation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;
public class StudentCourseRelationDaoImpl implements StudentCourseRelationDao {

    private final DBConnector connector;
    private static final String INSERT_COURSE = "INSERT INTO school.students_courses_relation (student_id, course_id)" +
            " VALUES (?, ?)";
    private static final String SELECT_ALL_COURSES_BY_STUDENT_ID = "SELECT * FROM school.students_courses_relation" +
            " WHERE student_id = ?";
    private static final String SELECT_ALL_STUDENTS_BY_COURSE_ID = "SELECT * FROM school.students_courses_relation" +
            " WHERE course_id = ?";

    private static final String DELETE_RELATION_BY_STUDENT_ID = "DELETE FROM school.students_courses_relation" +
            " WHERE student_id = ? AND course_id = ?";
    private static final String DELETE_ALL_RELATIONS_BY_STUDENT_ID = "DELETE FROM school.students_courses_relation" +
            " WHERE student_id = ?";
    private static final String DELETE_ALL_RELATIONS_BY_COURSE_ID = "DELETE FROM school.students_courses_relation" +
            " WHERE course_id = ?";


    public StudentCourseRelationDaoImpl(DBConnector connector) {
        this.connector = connector;
    }

    private StudentCourseRelation mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        StudentCourseRelation studentCourseRelation = StudentCourseRelation.builder()
                .withStudentID(resultSet.getInt("student_id"))
                .withCourseID(resultSet.getLong("course_id"))
                .build();

        return studentCourseRelation;
    }


    protected void insert(PreparedStatement preparedStatement, StudentCourseRelation entity) throws SQLException {
        preparedStatement.setLong(1, entity.getStudentID());
        preparedStatement.setLong(2, entity.getCourseID());
    }

    public void saveRelation(StudentCourseRelation studentCourseRelation) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COURSE)) {
            insert(preparedStatement, studentCourseRelation);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseRuntimeException("Insertion is failed", e);
        }
    }

    public List<StudentCourseRelation> findCoursesByStudentID(long studentID) {
        List<StudentCourseRelation> studentCourseRelations = new ArrayList<>();
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_COURSES_BY_STUDENT_ID)) {

            preparedStatement.setLong(1, studentID);
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                studentCourseRelations.add(mapResultSetToEntity(resultSet));
            }


        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
        return studentCourseRelations;
    }

    public List<StudentCourseRelation> findStudentsByCourseID(long courseID) {
        List<StudentCourseRelation> studentCourseRelations = new ArrayList<>();
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STUDENTS_BY_COURSE_ID)) {

            preparedStatement.setLong(1, courseID);
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                studentCourseRelations.add(mapResultSetToEntity(resultSet));
            }


        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
        return studentCourseRelations;
    }

    public void deleteRelationByStudentID(long studentID, long courseID) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RELATION_BY_STUDENT_ID)) {

            preparedStatement.setLong(1, studentID);
            preparedStatement.setLong(2, courseID);

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }

    public void deleteAllRelationsByStudentID(long studentID) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL_RELATIONS_BY_STUDENT_ID)) {

            preparedStatement.setLong(1, studentID);

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }

    public void deleteAllRelationsByCourseID(long courseID) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL_RELATIONS_BY_COURSE_ID)) {

            preparedStatement.setLong(1, courseID);

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }
}
