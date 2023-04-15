package com.foxminded.chendev.schoolconsoleapp.service.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.CourseDao;
import com.foxminded.chendev.schoolconsoleapp.dao.StudentDao;
import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseDao courseDao;
    private final StudentDao studentDao;

    public CourseServiceImpl(CourseDao courseDao, StudentDao studentDao) {

        this.courseDao = courseDao;
        this.studentDao = studentDao;
    }

    @Override
    @Transactional
    public List<Student> findAllStudentsByCourseName(String courseName) {

        long courseID = courseDao.findCourseByCourseName(courseName).getCourseId();

        return studentDao.findStudentsByCourseId(courseID);
    }

    @Override
    public List<Course> findAllCourses() {
        return courseDao.findAllCourses();
    }
}
