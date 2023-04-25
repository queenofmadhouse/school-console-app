package com.foxminded.chendev.schoolconsoleapp.datagenerator.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.CourseDao;
import com.foxminded.chendev.schoolconsoleapp.datagenerator.DataGenerator;
import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class CourseDataGenerator implements DataGenerator {
    private static final String[] COURSE_NAMES = {
            "Math", "Biology", "Physics", "Chemistry",
            "History", "Geography", "Literature", "Computer Science",
            "Physical Education", "Art"
    };
    private static final String[] DESCRIPTION_WORDS = {
            "fundamental", "concepts", "principles", "theories", "applications",
            "analysis", "techniques", "methods", "practice", "skills",
            "knowledge", "understanding", "critical", "thinking", "problem-solving",
            "collaboration", "communication", "innovation", "creativity", "research"
    };
    private static final Random random = new Random();
    private final CourseDao courseDao;
    private final int amountOfCourses;

    public CourseDataGenerator(CourseDao courseDao, int amountOfCourses) {

        this.courseDao = courseDao;
        this.amountOfCourses = amountOfCourses;
    }

    @Override
    public void generateData() {

        for (int i = 0; i < amountOfCourses; i++) {

            String courseName = generateCourseName(i);
            String courseDescription = generateCourseDescription();

            Course course = Course.builder()
                    .withCourseName(courseName)
                    .withCourseDescription(courseDescription)
                    .build();

            courseDao.save(course);
        }
    }

    private String generateCourseName(int index) {
        return COURSE_NAMES[index];
    }

    private String generateCourseDescription() {

        int wordCount = random.nextInt(5) + 5;
        StringBuilder description = new StringBuilder();

        for (int i = 0; i < wordCount; i++) {
            String word = DESCRIPTION_WORDS[random.nextInt(DESCRIPTION_WORDS.length)];
            description.append(word);

            if (i < wordCount - 1) {
                description.append(" ");
            }
        }

        return description.toString();
    }
}
