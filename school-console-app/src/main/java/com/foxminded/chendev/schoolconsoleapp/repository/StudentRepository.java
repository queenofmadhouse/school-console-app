package com.foxminded.chendev.schoolconsoleapp.repository;

import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM school.students_courses_relation " +
            "WHERE user_id = :userId AND course_id = :courseId", nativeQuery = true)
    void removeStudentFromCourse(@Param("userId")long studentId, @Param("courseId") long courseId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO school.students_courses_relation (user_id, course_id)" +
            " VALUES (:userId, :courseId)", nativeQuery = true)
    void addStudentToCourse(@Param("userId")long studentId, @Param("courseId")long courseId);

    @Transactional
    @Query(value = "SELECT s FROM Student s JOIN s.courses c WHERE c.courseId = :courseId")
    List<Student> findStudentsByCourseId(@Param("courseId")long courseId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM school.students_courses_relation " +
            "WHERE user_id = :userId", nativeQuery = true)
    void deleteAllRelationsByStudentId(@Param("userId")long studentId);

    @Transactional
    @Query(value = "SELECT s FROM Student s WHERE s.userId = :userId")
    Optional<Student> findById(@Param("userId") long userId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Student s WHERE s.userId = :userId")
    void deleteById(@Param("userId") long userId);
}
