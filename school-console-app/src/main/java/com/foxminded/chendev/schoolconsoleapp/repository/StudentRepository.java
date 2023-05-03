package com.foxminded.chendev.schoolconsoleapp.repository;

import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {

    @Modifying
    @Query(value = "DELETE FROM school.students_courses_relation " +
            "WHERE user_id = :userId AND course_id = :courseId", nativeQuery = true)
    void removeStudentFromCourse(@Param("userId")long studentId, @Param("courseId") long courseId);

    @Modifying
    @Query(value = "INSERT INTO school.students_courses_relation (user_id, course_id)" +
            " VALUES (:userId, :courseId)", nativeQuery = true)
    void addStudentToCourse(@Param("userId")long studentId, @Param("courseId")long courseId);

    @Query(value = "SELECT s FROM Student s JOIN s.courses c WHERE c.courseId = :courseId")
    List<Student> findStudentsByCourseId(@Param("courseId")long courseId);

    @Modifying
    @Query(value = "DELETE FROM school.students_courses_relation " +
            "WHERE user_id = :userId", nativeQuery = true)
    void deleteAllRelationsByStudentId(@Param("userId")long studentId);

    Optional<Student> findByUserId(@Param("userId") long userId);

    @Modifying
    void deleteByUserId(@Param("userId") long userId);
}
