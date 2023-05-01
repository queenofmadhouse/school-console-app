package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.StudentDao;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.exception.DataBaseRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class StudentDaoImpl extends AbstractCrudDao<Student> implements StudentDao {

    private static final String SELECT_ALL_STUDENTS = "SELECT s FROM Student s";
    private static final String INSERT_COURSE_RELATION = "INSERT INTO school.students_courses_relation (user_id, course_id)" +
            " VALUES (?, ?)";
    private static final String SELECT_ALL_STUDENTS_BY_COURSE_ID = "SELECT s FROM Student s JOIN s.courses c WHERE c.courseId = :course_id";

    private static final String DELETE_RELATION_BY_STUDENT_ID = "DELETE FROM school.students_courses_relation " +
            "WHERE user_id = ? AND course_id = ?";
    private static final String DELETE_ALL_RELATIONS_BY_STUDENT_ID = "DELETE FROM school.students_courses_relation " +
            "WHERE user_id = ?";
    private static final Logger logger = LoggerFactory.getLogger(StudentDaoImpl.class);

    public StudentDaoImpl(EntityManager entityManager) {
        super(entityManager, logger, SELECT_ALL_STUDENTS);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        super.deleteById(id);
        deleteAllRelationsByStudentId(id);
    }

    @Override
    protected Class<Student> getEntityClass() {
        return Student.class;
    }

    @Override
    @Transactional
    public void removeStudentFromCourse(long studentId, long courseId) {
        try {

            entityManager.createNativeQuery(DELETE_RELATION_BY_STUDENT_ID)
                    .setParameter(1, studentId)
                    .setParameter(2, courseId)
                    .executeUpdate();

            logger.info("Method removeStudentFromCourse was cold with parameters: " +
                    "StudentId: " + studentId +
                    ", CourseId: " + courseId);
        } catch (RuntimeException e) {

            logger.error("Exception in method removeStudentFromCourse with parameters: " +
                    "StudentId: " + studentId +
                    ", CourseId: " + courseId);

            throw new DataBaseRuntimeException("Can't remove from student with id: " + studentId +
                    ", from course with id: " + courseId, e);
        }
    }

    @Override
    @Transactional
    public void addStudentToCourse(long studentId, long courseId) {
        try {

            entityManager.createNativeQuery(INSERT_COURSE_RELATION).setParameter(1, studentId)
                    .setParameter(2, courseId).executeUpdate();

            logger.info("Method addStudentToCourse was cold with parameters: " +
                    "StudentId: " + studentId +
                    ", CourseId: " + courseId);
        } catch (RuntimeException e) {

            logger.error("Exception in method addStudentToCourse with parameters: " +
                    "StudentId: " + studentId +
                    ", CourseId: " + courseId);

            throw new DataBaseRuntimeException("Can't add student with id: " + studentId +
                    ", to course with id: " + courseId, e);
        }
    }

    @Override
    @Transactional
    public List<Student> findStudentsByCourseId(long courseId) {
        try {
            List<Student> resultList = entityManager.createQuery(SELECT_ALL_STUDENTS_BY_COURSE_ID, getEntityClass()).setParameter("course_id", courseId).getResultList();

            logger.info("Method findStudentsByCourseId was cold with parameters: " + courseId);

            return resultList;
        } catch (RuntimeException e) {
            logger.error("Exception in method findStudentsByCourseId with parameters: " + courseId, e);
            throw new DataBaseRuntimeException("Can't find students by course id: " + courseId, e);
        }
    }

    @Override
    @Transactional
    public void deleteAllRelationsByStudentId(long studentId) {
        try {

            entityManager.createNativeQuery(DELETE_ALL_RELATIONS_BY_STUDENT_ID).setParameter(1, studentId);

            logger.info("Method deleteAllRelationsByStudentId was cold with parameters: " + studentId);
        } catch (RuntimeException e) {

            logger.error("Exception in method deleteAllRelationsByStudentId with parameters: " + studentId, e);

            throw new DataBaseRuntimeException("Can't delete all relations by student id" + studentId, e);
        }
    }
}
