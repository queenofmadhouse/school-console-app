package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.CourseDao;
import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseDaoImpl extends AbstractCrudDao<Course> implements CourseDao {

    private static final String INSERT_COURSE = "INSERT INTO school.courses (course_name, course_description) " +
            "VALUES (?, ?)";
    private static final String SELECT_COURSE_BY_ID = "SELECT * FROM school.courses WHERE course_id = ?";
    private static final String SELECT_ALL_COURSES = "SELECT * FROM school.courses";
    private static final String UPDATE_COURSE = "UPDATE school.courses SET course_name = ?" +
            ", course_description = ? WHERE course_id = ?";
    private static final String DELETE_COURSE_BY_ID = "DELETE FROM school.courses WHERE course_id = ?";
    private static final String SELECT_COURSE_BY_NAME = "SELECT * FROM school.courses WHERE course_name = ?";
    private static final String SELECT_ALL_COURSES_BY_STUDENT_ID = "SELECT school.courses.*, school.students_courses_relation.student_id " +
            "FROM school.courses " +
            "INNER JOIN school.students_courses_relation ON school.courses.course_id = school.students_courses_relation.course_id " +
            "WHERE school.students_courses_relation.student_id = ?";
    private static final String DELETE_ALL_RELATIONS_BY_COURSE_ID = "DELETE FROM school.students_courses_relation" +
            " WHERE course_id = ?";
    private final JdbcTemplate jdbcTemplate;

    public CourseDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, INSERT_COURSE, SELECT_COURSE_BY_ID, SELECT_ALL_COURSES, UPDATE_COURSE, DELETE_COURSE_BY_ID);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Course> findAllCourses() {
        return super.findAll();
    }

    @Override
    public List<Course> findCoursesByStudentId(long studentId) {
        return jdbcTemplate.query(SELECT_ALL_COURSES_BY_STUDENT_ID, getRowMapper(), studentId);
    }

    @Override
    public void deleteAllRelationsByCourseId(long courseId) {
        jdbcTemplate.update(DELETE_ALL_RELATIONS_BY_COURSE_ID, courseId);
    }

    @Override
    public Course findCourseByCourseName(String courseName) {
        return jdbcTemplate.queryForObject(SELECT_COURSE_BY_NAME, new Object[]{courseName}, getRowMapper());
    }

    @Override
    protected RowMapper<Course> getRowMapper() {
        return (resultSet, rowNum) -> {

            return Course.builder()
                    .withCourseId(resultSet.getLong("course_id"))
                    .withCourseName(resultSet.getString("course_name"))
                    .withCourseDescription(resultSet.getString("course_description"))
                    .build();
        };
    }

    @Override
    protected Object[] getSaveParameters(Course course) {
        return new Object[]{course.getCourseName(), course.getCourseDescription()};
    }

    @Override
    protected Object[] getUpdateParameters(Course course) {
        return new Object[]{course.getCourseName(), course.getCourseDescription(), course.getCourseId()};
    }
}
