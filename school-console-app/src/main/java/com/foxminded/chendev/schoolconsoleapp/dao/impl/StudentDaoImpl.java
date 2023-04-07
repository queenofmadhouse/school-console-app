package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.DBConnector;
import com.foxminded.chendev.schoolconsoleapp.dao.StudentDao;
import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentDaoImpl extends AbstractCrudDao<Student> implements StudentDao {

    private final DBConnector connector;
    private static final String INSERT_STUDENT = "INSERT INTO school.students (group_id, first_name, last_name) VALUES (?, ?, ?)";
    private static final String SELECT_STUDENT_BY_ID = "SELECT * FROM school.students WHERE student_id = ?";
    private static final String SELECT_ALL_STUDENTS = "SELECT * FROM school.students";
    private static final String UPDATE_STUDENT = "UPDATE school.students SET group_id = ?, first_name = ?, last_name = ? WHERE student_id = ?";
    private static final String DELETE_STUDENT_BY_ID = "DELETE FROM school.students WHERE student_id = ?";

    public StudentDaoImpl(DBConnector connector) {
        super(connector, INSERT_STUDENT, SELECT_STUDENT_BY_ID, SELECT_ALL_STUDENTS, UPDATE_STUDENT, DELETE_STUDENT_BY_ID);
        this.connector = connector;
    }

    @Override
    protected Student mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Student.builder()
                .withStudentId(resultSet.getInt("student_id"))
                .withGroupId(Group.builder().withGroupId(resultSet.getLong("group_id")).build())
                .withFirstName(resultSet.getString("first_name"))
                .withLastName(resultSet.getString("last_name"))
                .build();
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, Student entity) throws SQLException {
        preparedStatement.setLong(1, entity.getGroupId());
        preparedStatement.setString(2, entity.getFirstName());
        preparedStatement.setString(3, entity.getLastName());
    }

    protected void updateValues(PreparedStatement preparedStatement, Student entity) throws SQLException {
        preparedStatement.setLong(1, entity.getGroupId());
        preparedStatement.setString(2, entity.getFirstName());
        preparedStatement.setString(3, entity.getLastName());
        preparedStatement.setLong(4, entity.getStudentId());
    }

    @Override
    public void deleteByID(long id) {
        super.deleteByID(id);
        new CourseDaoImpl(connector).deleteAllRelationsByStudentID(id);
    }
}
