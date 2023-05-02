package com.foxminded.chendev.schoolconsoleapp.datagenerator.impl;

import com.foxminded.chendev.schoolconsoleapp.datagenerator.DataGenerator;
import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.repository.CourseRepository;
import com.foxminded.chendev.schoolconsoleapp.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class StudentCourseRelationDataGenerator implements DataGenerator {

    private static final Random random = new Random();
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public StudentCourseRelationDataGenerator(StudentRepository studentRepository, CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void generateData() {
        List<Student> students = studentRepository.findAll();
        List<Course> courses = courseRepository.findAll();

        for (Student student : students) {
            int courseCount = random.nextInt(3) + 1;

            for (int i = 0; i < courseCount; i++) {
                Course course = getRandonmCourse(courses);

                studentRepository.addStudentToCourse(student.getUserId(), course.getCourseId());
            }
        }
    }

    private Course getRandonmCourse(List<Course> courses) {
        int courseIndex = random.nextInt(courses.size());
        return courses.get(courseIndex);
    }
}
