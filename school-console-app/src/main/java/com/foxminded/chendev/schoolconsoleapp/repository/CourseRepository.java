package com.foxminded.chendev.schoolconsoleapp.repository;

import com.foxminded.chendev.schoolconsoleapp.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Transactional
    @Query(value = "SELECT c FROM Course c WHERE c.courseId = :courseId")
    Optional<Course> findById(@Param("courseId") long courseId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Course c WHERE c.courseId = :courseId")
    void deleteById(@Param("courseId") long courseId);

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
    @Query(value = "SELECT c FROM Course c WHERE c.courseName = :courseName")
    Optional<Course> findCourseByName(@Param("courseName") String courseName);
}
