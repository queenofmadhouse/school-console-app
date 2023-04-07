package com.foxminded.chendev.schoolconsoleapp.generator.datagenegator;

import com.foxminded.chendev.schoolconsoleapp.dao.CourseDao;
import com.foxminded.chendev.schoolconsoleapp.dao.StudentDao;
import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.entity.StudentCourseRelation;

import java.util.List;
import java.util.Random;

public class StudentsCoursesRelationsGenerator implements DataGenerator {

    private static Random random = new Random();
    private final StudentDao studentDao;
    private final CourseDao courseDao;

    public StudentsCoursesRelationsGenerator(StudentDao studentDao, CourseDao courseDao) {

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

                StudentCourseRelation studentCourseRelation = StudentCourseRelation.builder()
                        .withStudentId(student.getStudentId())
                        .withCourseId(course.getCourseId())
                        .build();
                courseDao.saveRelation(studentCourseRelation);
            }
        }
    }

    private Course getRandonmCourse(List<Course> courses) {

        int courseIndex = random.nextInt(courses.size());
        return courses.get(courseIndex);
    }
}
