package com.foxminded.chendev.schoolconsoleapp.service.impl;

import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.exception.DataBaseRuntimeException;
import com.foxminded.chendev.schoolconsoleapp.repository.CourseRepository;
import com.foxminded.chendev.schoolconsoleapp.repository.StudentRepository;
import com.foxminded.chendev.schoolconsoleapp.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentServiceImpl(StudentRepository studentRepository, CourseRepository courseRepository) {

        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void save(Student entity) {
        try {
            studentRepository.save(entity);
        } catch (Exception e) {
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    public Optional<Student> findById(long id) {
        try {
            return studentRepository.findByUserId(id);
        } catch (Exception e) {
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    public void deleteById(long id) {
        try {
            studentRepository.deleteByUserId(id);

        } catch (Exception e) {
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    public void addStudentToCourse(long studentId, long courseId) {
        try {
            studentRepository.addStudentToCourse(studentId, courseId);
        } catch (Exception e) {
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    public void removeStudentFromCourse(long studentId, long courseId) {
        try {
            studentRepository.removeStudentFromCourse(studentId, courseId);
        } catch (Exception e) {
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    @Transactional
    public List<Student> findAllStudentsByCourseName(String courseName) {
        try {

            Optional<Course> course = courseRepository.findByCourseName(courseName);

            if (course.isPresent()) {
                long courseID = course.get().getCourseId();
                return studentRepository.findStudentsByCourseId(courseID);
            } else {
                return Collections.emptyList();
            }
        } catch (Exception e) {
            throw new DataBaseRuntimeException(e);
        }
    }
}
