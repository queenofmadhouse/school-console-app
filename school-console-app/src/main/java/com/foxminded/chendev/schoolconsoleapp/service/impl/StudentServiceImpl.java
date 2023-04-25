package com.foxminded.chendev.schoolconsoleapp.service.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.CourseDao;
import com.foxminded.chendev.schoolconsoleapp.dao.StudentDao;
import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;
    private final CourseDao courseDao;

    public StudentServiceImpl(StudentDao studentDao, CourseDao courseDao) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
    }

    @Override
    public void save(Student entity) {
        studentDao.save(entity);
    }

    @Override
    public Optional<Student> findById(long id) {
        return studentDao.findById(id);
    }

    @Override
    public void deleteById(long id) {
        studentDao.deleteById(id);
    }

    @Override
    public void addStudentToCourse(long studentId, long courseId) {
        studentDao.addStudentToCourse(studentId, courseId);
    }

    @Override
    public void removeStudentFromCourse(long studentId, long courseId) {
        studentDao.removeStudentFromCourse(studentId, courseId);
    }

    @Override
    @Transactional
    public List<Student> findAllStudentsByCourseName(String courseName) {

        Optional<Course> course = courseDao.findCourseByName(courseName);

        if (course.isPresent()) {
            long courseID = course.get().getCourseId();
            return studentDao.findStudentsByCourseId(courseID);
        } else {
            return Collections.emptyList();
        }
    }
}
