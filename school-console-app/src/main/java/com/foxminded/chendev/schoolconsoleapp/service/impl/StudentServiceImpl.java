package com.foxminded.chendev.schoolconsoleapp.service.impl;

import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
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

    @Transactional
    @Override
    public void save(Student entity) {

        studentRepository.save(entity);
    }

    @Override
    public Optional<Student> findById(long id) {

        return studentRepository.findByUserId(id);
    }

    @Transactional
    @Override
    public void deleteById(long id) {

        studentRepository.deleteByUserId(id);
    }

    @Transactional
    @Override
    public void addStudentToCourse(long studentId, long courseId) {

        studentRepository.addStudentToCourse(studentId, courseId);
    }

    @Transactional
    @Override
    public void removeStudentFromCourse(long studentId, long courseId) {

        studentRepository.removeStudentFromCourse(studentId, courseId);
    }

    @Override
    @Transactional
    public List<Student> findAllStudentsByCourseName(String courseName) {

        Optional<Course> course = courseRepository.findByCourseName(courseName);

        if (course.isPresent()) {
            long courseID = course.get().getCourseId();
            return studentRepository.findStudentsByCourseId(courseID);
        } else {
            return Collections.emptyList();
        }
    }
}
