package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.StudentDao;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class StudentDaoImpl extends AbstractCrudDao<Student> implements StudentDao {

    private static final String INSERT_STUDENT = "INSERT INTO school.students (group_id, first_name, last_name) VALUES (?, ?, ?)";
    private static final String SELECT_STUDENT_BY_ID = "SELECT * FROM school.students WHERE student_id = ?";
    private static final String SELECT_ALL_STUDENTS = "SELECT * FROM school.students";
    private static final String UPDATE_STUDENT = "UPDATE school.students SET group_id = ?, first_name = ?, last_name = ? WHERE student_id = ?";
    private static final String DELETE_STUDENT_BY_ID = "DELETE FROM school.students WHERE student_id = ?";
    private static final String INSERT_COURSE_RELATION = "INSERT INTO school.students_courses_relation (student_id, course_id)" +
            " VALUES (?, ?)";
    private static final String SELECT_ALL_STUDENTS_BY_COURSE_ID = "SELECT school.students.* " +
            "FROM school.students " +
            "INNER JOIN school.students_courses_relation ON school.students.student_id = school.students_courses_relation.student_id " +
            "WHERE school.students_courses_relation.course_id = ?";
    private static final String DELETE_RELATION_BY_STUDENT_ID = "DELETE FROM school.students_courses_relation" +
            " WHERE student_id = ? AND course_id = ?";
    private static final String DELETE_ALL_RELATIONS_BY_STUDENT_ID = "DELETE FROM school.students_courses_relation" +
            " WHERE student_id = ?";
    private final JdbcTemplate jdbcTemplate;

    public StudentDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, INSERT_STUDENT, SELECT_STUDENT_BY_ID, SELECT_ALL_STUDENTS, UPDATE_STUDENT, DELETE_STUDENT_BY_ID);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        super.deleteById(id);
        deleteAllRelationsByStudentId(id);
    }

    @Override
    public void removeStudentFromCourse(long studentId, long courseId) { //
        jdbcTemplate.update(DELETE_RELATION_BY_STUDENT_ID, studentId, courseId);
    }

    @Override
    public void addStudentToCourse(long studentId, long courseId) { //
        jdbcTemplate.update(INSERT_COURSE_RELATION, studentId, courseId);
    }

    @Override
    public List<Student> findStudentsByCourseId(long courseId) {
        return jdbcTemplate.query(SELECT_ALL_STUDENTS_BY_COURSE_ID, getRowMapper(), courseId);
    }

    @Override
    public void deleteAllRelationsByStudentId(long studentId) { //
        jdbcTemplate.update(DELETE_ALL_RELATIONS_BY_STUDENT_ID, studentId);
    }

    @Override
    public void deleteRelationByStudentId(long studentId, long courseId) {
        jdbcTemplate.update(DELETE_RELATION_BY_STUDENT_ID, studentId, courseId);
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
