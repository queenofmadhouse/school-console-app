package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.CourseDao;
import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.entity.StudentCourseRelation;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
    private final JdbcTemplate jdbcTemplate;

    public CourseDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, INSERT_COURSE, SELECT_COURSE_BY_ID, SELECT_ALL_COURSES, UPDATE_COURSE, DELETE_COURSE_BY_ID);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveRelation(StudentCourseRelation studentCourseRelation) {
        jdbcTemplate.update(INSERT_COURSE_RELATION, studentCourseRelation.getStudentId(), studentCourseRelation.getCourseId());
    }

    @Override
    public List<StudentCourseRelation> findCoursesByStudentID(long studentID) {
        return jdbcTemplate.query(SELECT_ALL_COURSES_BY_STUDENT_ID, studentCourseRelationRowMapper(), studentID);
    }

    @Override
    public List<StudentCourseRelation> findStudentsByCourseID(long courseID) {
        return jdbcTemplate.query(SELECT_ALL_STUDENTS_BY_COURSE_ID, studentCourseRelationRowMapper(), courseID);
    }

    @Override
    public void deleteRelationByStudentID(long studentID, long courseID) {
        jdbcTemplate.update(DELETE_RELATION_BY_STUDENT_ID, studentID, courseID);
    }

    @Override
    public void deleteAllRelationsByStudentID(long studentID) {
        jdbcTemplate.update(DELETE_ALL_RELATIONS_BY_STUDENT_ID, studentID);
    }

    @Override
    public void deleteAllRelationsByCourseID(long courseID) {
        jdbcTemplate.update(DELETE_ALL_RELATIONS_BY_COURSE_ID, courseID);
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

    public RowMapper<StudentCourseRelation> studentCourseRelationRowMapper() {
        return (resultSet, rowNum) -> {

            return StudentCourseRelation.builder()
                    .withStudentId(resultSet.getInt("student_id"))
                    .withCourseId(resultSet.getLong("course_id"))
                    .build();
        };
    }

    @Override
    public Course findCourseByCourseName(String courseName) {
        return jdbcTemplate.queryForObject(SELECT_COURSE_BY_NAME, new Object[]{courseName}, getRowMapper());
    }

    @Override
    public void addStudentToCourse(Student student, long courseID) {
        jdbcTemplate.update(INSERT_COURSE_RELATION, student.getStudentId(), courseID);
    }

    @Override
    public void removeStudentFromCourse(long studentID, long courseID) {
        jdbcTemplate.update(DELETE_RELATION_BY_STUDENT_ID, studentID, courseID);
    }

    @Override
    public List<Student> findAllStudentsByCourseName(String courseName) {
        List<Student> students = new ArrayList<>();
        long courseID = findCourseByCourseName(courseName).getCourseId();
        List<StudentCourseRelation> studentCourseRelation = findStudentsByCourseID(courseID);

        StudentDaoImpl studentDao = new StudentDaoImpl(jdbcTemplate);

        for (StudentCourseRelation courseRelation : studentCourseRelation) {
            Student tempStudent = studentDao.findById(courseRelation.getStudentId());
            students.add(tempStudent);
        }

        return students;
    }

    @Override
    public void deleteByID(long id) {
        super.deleteByID(id);
        deleteAllRelationsByCourseID(id);
    }
}
