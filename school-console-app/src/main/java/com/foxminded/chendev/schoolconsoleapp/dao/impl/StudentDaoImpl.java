package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.exception.DataBaseRuntimeException;
import com.foxminded.chendev.schoolconsoleapp.dao.StudentDao;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class StudentDaoImpl extends AbstractCrudDao<Student> implements StudentDao {

    private static final String INSERT_USER_AND_STUDENT = "WITH new_user AS (INSERT INTO school.users (first_name, last_name) " +
            "VALUES (?, ?) RETURNING user_id) INSERT INTO school.students (user_id, group_id) SELECT user_id, ? FROM new_user";
    private static final String SELECT_STUDENT_BY_ID = "SELECT a.user_id, a.group_id, b.first_name, b.last_name " +
            "FROM school.students a JOIN school.users b ON a.user_id = b.user_id WHERE a.user_id = ?";
    private static final String SELECT_ALL_STUDENTS = "SELECT a.user_id, a.group_id, b.first_name, b.last_name " +
            "FROM school.students a JOIN school.users b ON a.user_id = b.user_id";
    private static final String UPDATE_STUDENT = "WITH updated_students AS ( UPDATE school.students SET group_id = ?\n" +
            "WHERE user_id = ? RETURNING user_id) UPDATE school.users SET first_name = ?, last_name = ?\n" +
            "WHERE user_id IN (SELECT user_id FROM updated_students);";
    private static final String DELETE_STUDENT_BY_ID = "WITH deleted_students AS ( DELETE FROM school.students WHERE " +
            "user_id = ? RETURNING user_id ) DELETE FROM school.users WHERE user_id IN (SELECT user_id FROM deleted_students);";
    private static final String INSERT_COURSE_RELATION = "INSERT INTO school.students_courses_relation (user_id, course_id)" +
            " VALUES (?, ?)";
    private static final String SELECT_ALL_STUDENTS_BY_COURSE_ID = "SELECT a.user_id, a.group_id, b.first_name, b.last_name " +
            "FROM school.students a " +
            "JOIN school.users b ON a.user_id = b.user_id " +
            "JOIN school.students_courses_relation c ON a.user_id = c.user_id WHERE c.course_id = ?";
    private static final String DELETE_RELATION_BY_STUDENT_ID = "DELETE FROM school.students_courses_relation " +
            "WHERE user_id = ? AND course_id = ?";
    private static final String DELETE_ALL_RELATIONS_BY_STUDENT_ID = "DELETE FROM school.students_courses_relation " +
            "WHERE user_id = ?";

    public StudentDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, INSERT_USER_AND_STUDENT, SELECT_STUDENT_BY_ID,
                SELECT_ALL_STUDENTS, UPDATE_STUDENT, DELETE_STUDENT_BY_ID);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        super.deleteById(id);
        deleteAllRelationsByStudentId(id);
    }

    @Override
    public void removeStudentFromCourse(long studentId, long courseId) {
        try {
            jdbcTemplate.update(DELETE_RELATION_BY_STUDENT_ID, studentId, courseId);
        } catch (DataAccessException e) {
            throw new DataBaseRuntimeException("Can't remove from student with id: " + studentId +
                    ", from course with id: " + courseId, e);
        }
    }

    @Override
    public void addStudentToCourse(long studentId, long courseId) {
        try {
            jdbcTemplate.update(INSERT_COURSE_RELATION, studentId, courseId);
        } catch (DataAccessException e) {
            throw new DataBaseRuntimeException("Can't add student with id: " + studentId +
                    ", to course with id: " + courseId, e);
        }
    }

    @Override
    public List<Student> findStudentsByCourseId(long courseId) {
        try {
            return jdbcTemplate.query(SELECT_ALL_STUDENTS_BY_COURSE_ID, getRowMapper(), courseId);
        } catch (DataAccessException e) {
            throw new DataBaseRuntimeException("Can't find students by course id: " + courseId, e);
        }
    }

    @Override
    public void deleteAllRelationsByStudentId(long studentId) {
        try {
            jdbcTemplate.update(DELETE_ALL_RELATIONS_BY_STUDENT_ID, studentId);
        } catch (DataAccessException e) {
            throw new DataBaseRuntimeException("Can't delete all relations by student id" + studentId, e);
        }
    }

    @Override
    public void deleteRelationByStudentId(long studentId, long courseId) {
        try {
            jdbcTemplate.update(DELETE_RELATION_BY_STUDENT_ID, studentId, courseId);
        } catch (DataAccessException e) {
            throw new DataBaseRuntimeException("Can't delete relation by student id: " + studentId +
                    ", and course id: " + courseId, e);
        }
    }

    @Override
    protected RowMapper<Student> getRowMapper() {
        return (resultSet, rowNum) -> {

            return Student.builder()
                    .withUserId(resultSet.getInt("user_id"))
                    .withGroupId(resultSet.getLong("group_id"))
                    .withFirstName(resultSet.getString("first_name"))
                    .withLastName(resultSet.getString("last_name"))
                    .build();
        };
    }

    @Override
    protected Object[] getUpdateParameters(Student student) {
        return new Object[]{student.getGroupId(), student.getUserId(), student.getFirstName(), student.getLastName()};
    }

    @Override
    protected Object[] getSaveParameters(Student student) {
        return new Object[]{student.getFirstName(), student.getLastName(), student.getGroupId()};
    }

}
