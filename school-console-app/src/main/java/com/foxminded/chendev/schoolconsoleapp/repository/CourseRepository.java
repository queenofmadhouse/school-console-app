package com.foxminded.chendev.schoolconsoleapp.repository;

import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {

    @Transactional
    Optional<Course> findByCourseId(@Param("courseId") long courseId);

    @Transactional
    @Modifying
    void deleteByCourseId(@Param("courseId") long courseId);

    @Transactional
    @Query(value = "SELECT c FROM Course c JOIN c.students s " +
            "WHERE s.userId = :userId")
    List<Course> findCoursesByStudentId(@Param("userId") long studentId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM school.students_courses_relation " +
            "WHERE course_id = :courseId", nativeQuery = true)
    void deleteAllRelationsByCourseId(@Param("courseId") long courseId);

    @Transactional
    Optional<Course> findByCourseName(@Param("courseName") String courseName);
}
