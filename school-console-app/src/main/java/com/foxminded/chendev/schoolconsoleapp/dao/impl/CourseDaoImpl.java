package com.foxminded.chendev.schoolconsoleapp.dao.impl;


import com.foxminded.chendev.schoolconsoleapp.dao.CourseDao;
import com.foxminded.chendev.schoolconsoleapp.dao.DBConnector;
import com.foxminded.chendev.schoolconsoleapp.dao.DataBaseRuntimeException;
import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.entity.StudentCourseRelation;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseDaoImpl extends AbstractCrudDao<Course> implements CourseDao {

    private static final String INSERT_COURSE = "INSERT INTO school.courses (course_name, course_description) " +
            "VALUES (?, ?)";
    private static final String SELECT_COURSE_BY_ID = "SELECT * FROM school.courses WHERE course_id = ?";
    private static final String SELECT_ALL_COURSES = "SELECT * FROM school.courses";
    private static final String UPDATE_COURSE = "UPDATE school.courses SET course_name = ?" +
            ", course_description = ? WHERE course_id = ?";
    private static final String DELETE_COURSE_BY_ID = "DELETE FROM school.courses WHERE course_id = ?";
    private static final String SELECT_COURSE_BY_NAME = "SELECT * FROM school.courses WHERE course_name = ?";
    private static final String INSERT_COURSE_RELATION = "INSERT INTO school.students_courses_relation (student_id, course_id)" +
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
    private final DBConnector connector;

    public CourseDaoImpl(DBConnector connector) {
        super(connector, INSERT_COURSE, SELECT_COURSE_BY_ID, SELECT_ALL_COURSES, UPDATE_COURSE, DELETE_COURSE_BY_ID);
        this.connector = connector;
    }

    @Override
    protected Course mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Course.builder()
                .withCourseId(resultSet.getLong("course_id"))
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
        preparedStatement.setLong(3, entity.getCourseId());
    }

    @Override
    public Course findCourseByCourseName(String courseName) {
        return super.findByStringParam(courseName, SELECT_COURSE_BY_NAME).orElseThrow(DataBaseRuntimeException::new);
    }

    @Override
    public void addStudentToCourse(Student student, long courseID) {
        StudentCourseRelation studentCourseRelation = StudentCourseRelation.builder()
                .withStudentId(student.getStudentId())
                .withCourseId(courseID)
                .build();

        saveRelation(studentCourseRelation);
    }

    @Override
    public void removeStudentFromCourse(long studentID, long courseID) {
        deleteRelationByStudentID(studentID, courseID);
    }

    @Override
    public List<Student> findAllStudentsByCourseName(String courseName) {

        List<Student> students = new ArrayList<>();
        long courseID = findCourseByCourseName(courseName).getCourseId();
        List<StudentCourseRelation> studentCourseRelation = findStudentsByCourseID(courseID);

        StudentDaoImpl studentDao = new StudentDaoImpl(connector);

        for (StudentCourseRelation courseRelation : studentCourseRelation) {
            Student tempStudent = studentDao.findById(courseRelation.getStudentId()).orElseThrow(DataBaseRuntimeException::new);
            students.add(tempStudent);
        }

        return students;
    }

    @Override
    public void deleteByID(long id) {
        super.deleteByID(id);
        deleteAllRelationsByCourseID(id);
    }

    @Override
    public StudentCourseRelation mapResultSetToRelationEntity(ResultSet resultSet) throws SQLException {
        return StudentCourseRelation.builder()
                .withStudentId(resultSet.getInt("student_id"))
                .withCourseId(resultSet.getLong("course_id"))
                .build();
    }

    @Override
    public void insertRelation(PreparedStatement preparedStatement, StudentCourseRelation entity) throws SQLException {
        preparedStatement.setLong(1, entity.getStudentId());
        preparedStatement.setLong(2, entity.getCourseId());
    }

    @Override
    public void saveRelation(StudentCourseRelation studentCourseRelation) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COURSE_RELATION)) {
            insertRelation(preparedStatement, studentCourseRelation);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseRuntimeException("Insertion is failed", e);
        }
    }

    @Override
    public List<StudentCourseRelation> findCoursesByStudentID(long studentID) {
        List<StudentCourseRelation> studentCourseRelations = new ArrayList<>();
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_COURSES_BY_STUDENT_ID)) {

            preparedStatement.setLong(1, studentID);
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                studentCourseRelations.add(mapResultSetToRelationEntity(resultSet));
            }


        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
        return studentCourseRelations;
    }

    @Override
    public List<StudentCourseRelation> findStudentsByCourseID(long courseID) {
        List<StudentCourseRelation> studentCourseRelations = new ArrayList<>();
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_STUDENTS_BY_COURSE_ID)) {

            preparedStatement.setLong(1, courseID);
            final ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                studentCourseRelations.add(mapResultSetToRelationEntity(resultSet));
            }


        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
        return studentCourseRelations;
    }

    @Override
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

    @Override
    public void deleteAllRelationsByStudentID(long studentID) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ALL_RELATIONS_BY_STUDENT_ID)) {

            preparedStatement.setLong(1, studentID);

            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
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
