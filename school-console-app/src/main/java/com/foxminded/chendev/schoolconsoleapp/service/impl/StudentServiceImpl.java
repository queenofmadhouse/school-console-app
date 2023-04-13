package com.foxminded.chendev.schoolconsoleapp.service.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.StudentDao;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.service.StudentService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {

    private final StudentDao studentDao;

    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
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
}
