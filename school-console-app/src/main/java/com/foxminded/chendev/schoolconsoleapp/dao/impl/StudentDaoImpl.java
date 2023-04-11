package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.StudentDao;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDaoImpl extends AbstractCrudDao<Student> implements StudentDao {

    private static final String INSERT_STUDENT = "INSERT INTO school.students (group_id, first_name, last_name) VALUES (?, ?, ?)";
    private static final String SELECT_STUDENT_BY_ID = "SELECT * FROM school.students WHERE student_id = ?";
    private static final String SELECT_ALL_STUDENTS = "SELECT * FROM school.students";
    private static final String UPDATE_STUDENT = "UPDATE school.students SET group_id = ?, first_name = ?, last_name = ? WHERE student_id = ?";
    private static final String DELETE_STUDENT_BY_ID = "DELETE FROM school.students WHERE student_id = ?";
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public StudentDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, INSERT_STUDENT, SELECT_STUDENT_BY_ID, SELECT_ALL_STUDENTS, UPDATE_STUDENT, DELETE_STUDENT_BY_ID);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void deleteByID(long id) {
        super.deleteByID(id);
        new CourseDaoImpl(jdbcTemplate).deleteAllRelationsByStudentID(id);
    }

    @Override
    protected RowMapper<Student> getRowMapper() {
        return (resultSet, rowNum) -> {

            return Student.builder()
                    .withStudentId(resultSet.getInt("student_id"))
                    .withGroupId(resultSet.getLong("group_id"))
                    .withFirstName(resultSet.getString("first_name"))
                    .withLastName(resultSet.getString("last_name"))
                    .build();
        };
    }

    @Override
    protected Object[] getUpdateParameters(Student student) {
        return new Object[]{student.getGroupId(), student.getFirstName(), student.getLastName(), student.getStudentId()};
    }

    @Override
    protected Object[] getSaveParameters(Student student) {
        return new Object[]{student.getGroupId(), student.getFirstName(), student.getLastName()};
    }
}
