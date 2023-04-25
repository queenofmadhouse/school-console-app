package com.foxminded.chendev.schoolconsoleapp.datagenerator.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.CourseDao;
import com.foxminded.chendev.schoolconsoleapp.dao.StudentDao;
import com.foxminded.chendev.schoolconsoleapp.datagenerator.DataGenerator;
import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class StudentCourseRelationDataGenerator implements DataGenerator {

    private static final Random random = new Random();
    private final StudentDao studentDao;
    private final CourseDao courseDao;

    public StudentCourseRelationDataGenerator(StudentDao studentDao, CourseDao courseDao) {
        this.studentDao = studentDao;
        this.courseDao = courseDao;
    }

    @Override
    public void generateData() {
        List<Student> students = studentDao.findAll();
        List<Course> courses = courseDao.findAll();

        for (Student student : students) {
            int courseCount = random.nextInt(3) + 1;

            for (int i = 0; i < courseCount; i++) {
                Course course = getRandonmCourse(courses);

                studentDao.addStudentToCourse(student.getUserId(), course.getCourseId());
            }
        }
    }

    private Course getRandonmCourse(List<Course> courses) {
        int courseIndex = random.nextInt(courses.size());
        return courses.get(courseIndex);
    }
}
