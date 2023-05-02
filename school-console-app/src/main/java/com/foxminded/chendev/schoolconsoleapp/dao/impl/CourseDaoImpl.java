package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.CourseDao;
import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.exception.DataBaseRuntimeException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Repository
public class CourseDaoImpl extends AbstractCrudDao<Course> implements CourseDao {

    private static final String SELECT_ALL_COURSES = "SELECT g FROM Course g";
    private static final String SELECT_COURSE_BY_NAME = "SELECT c FROM Course c WHERE c.courseName = :course_name";
    private static final String SELECT_ALL_COURSES_BY_STUDENT_ID = "SELECT c FROM Course c JOIN c.students s " +
            "WHERE s.userId = : user_id";
    private static final String DELETE_ALL_RELATIONS_BY_COURSE_ID = "DELETE FROM school.students_courses_relation" +
            " WHERE course_id = :course_id";

    public CourseDaoImpl(EntityManager entityManager) {
        super(entityManager, SELECT_ALL_COURSES);
    }

    @Override
    public List<Course> findAllCourses() {
        return super.findAll();
    }

    @Override
    @Transactional
    public List<Course> findCoursesByStudentId(long studentId) {
        try {

            List<Course> resultList = entityManager.createQuery(SELECT_ALL_COURSES_BY_STUDENT_ID, Course.class)
                    .setParameter("user_id", studentId).getResultList();

            return resultList;
        } catch (RuntimeException e) {

            throw new DataBaseRuntimeException("Can't find course by student id: " + studentId, e);
        }
    }

    @Override
    @Transactional
    public void deleteAllRelationsByCourseId(long courseId) {
        try {

            entityManager.createNativeQuery(DELETE_ALL_RELATIONS_BY_COURSE_ID)
                    .setParameter("course_id", courseId).executeUpdate();
        } catch (RuntimeException e) {

            throw new DataBaseRuntimeException("Can't delete all relations by course id: " + courseId, e);
        }
    }

    @Override
    @Transactional
    public Optional<Course> findCourseByName(String courseName) {
        try {

            Optional<Course> resultOptional = Optional.ofNullable(
                    entityManager.createQuery(SELECT_COURSE_BY_NAME, Course.class)
                    .setParameter("course_name", courseName).getSingleResult()
            );

            return resultOptional;
        } catch (NoResultException e) {

            return Optional.empty();
        } catch (RuntimeException e) {

            throw new DataBaseRuntimeException("Can't find course by name: " + courseName);
        }
    }

    @Override
    protected Class<Course> getEntityClass() {
        return Course.class;
    }
}
