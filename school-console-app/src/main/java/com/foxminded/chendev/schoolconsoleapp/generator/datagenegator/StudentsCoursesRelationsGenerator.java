package com.foxminded.chendev.schoolconsoleapp.generator.datagenegator;

import com.foxminded.chendev.schoolconsoleapp.dao.impl.CourseDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.dao.impl.StudentCourseRelationDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.dao.impl.StudentDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.entity.StudentCourseRelation;

import java.util.List;
import java.util.Random;

public class StudentsCoursesRelationsGenerator implements DataGenerator {

    private static Random random = new Random();
    private final StudentCourseRelationDaoImpl studentCourseRelationDao;
    private final StudentDaoImpl studentDao;
    private final CourseDaoImpl courseDao;

    public StudentsCoursesRelationsGenerator(StudentCourseRelationDaoImpl studentCourseRelationDao,
                                             StudentDaoImpl studentDao, CourseDaoImpl courseDao) {
        this.studentCourseRelationDao = studentCourseRelationDao;
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
                        .withStudentID(student.getStudentID())
                        .withCourseID(course.getCourseID())
                        .build();
                studentCourseRelationDao.saveRelation(studentCourseRelation);
            }
        }
    }

    private Course getRandonmCourse(List<Course> courses) {
        int courseIndex = random.nextInt(courses.size());
        return courses.get(courseIndex);
    }
}
