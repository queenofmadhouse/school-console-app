package com.foxminded.chendev.schoolconsoleapp.service.impl;

import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.repository.CourseRepository;
import com.foxminded.chendev.schoolconsoleapp.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {

        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> findAllCourses() {

        return courseRepository.findAll();
    }
}
