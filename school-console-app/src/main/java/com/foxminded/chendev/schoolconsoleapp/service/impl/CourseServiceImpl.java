package com.foxminded.chendev.schoolconsoleapp.service.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.DataBaseRuntimeException;
import com.foxminded.chendev.schoolconsoleapp.dao.impl.CourseDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.dao.impl.StudentDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.entity.StudentCourseRelation;
import com.foxminded.chendev.schoolconsoleapp.service.CourseService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseDaoImpl courseDao;
    public final StudentDaoImpl studentDao;

    public CourseServiceImpl(CourseDaoImpl courseDao, StudentDaoImpl studentDao) {
        this.courseDao = courseDao;
        this.studentDao = studentDao;
    }

    @Override
    public void addStudentToCourse(Student student, long courseID) {
        courseDao.addStudentToCourse(student, courseID);
    }

    @Override
    public void removeStudentFromCourse(long studentID, long courseID) {
        courseDao.removeStudentFromCourse(studentID, courseID);
    }

    @Override
    public List<Student> findAllStudentsByCourseName(String courseName) {
        List<Student> students = new ArrayList<>();
        long courseID = courseDao.findCourseByCourseName(courseName).getCourseId();
        List<StudentCourseRelation> studentCourseRelation = courseDao.findStudentsByCourseID(courseID);

        for (StudentCourseRelation courseRelation : studentCourseRelation) {
            Student tempStudent = studentDao.findById(courseRelation.getStudentId()).orElseThrow(DataBaseRuntimeException::new);
            students.add(tempStudent);
        }

        return students;
    }

    @Override
    public List<Course> findAll() {
        return courseDao.findAll();
    }
}
