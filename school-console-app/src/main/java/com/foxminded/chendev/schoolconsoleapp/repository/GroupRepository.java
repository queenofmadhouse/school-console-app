package com.foxminded.chendev.schoolconsoleapp.repository;

import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {

    @Transactional
    @Query(value = "SELECT g FROM Group g WHERE g.groupId = :groupId")
    Optional<Group> findById(@Param("groupId") long groupId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM Group g WHERE g.groupId = :groupId")
    void deleteById(@Param("groupId") long groupId);

    @Transactional
    @Query(value = "SELECT g.group_id, g.group_name, " +
            "COUNT(s.user_id) as student_count FROM school.groups g " +
            "LEFT JOIN school.students s ON g.group_id = s.group_id " +
            "GROUP BY g.group_id, g.group_name " +
            "HAVING COUNT(s.user_id) <= :maxStudentCount", nativeQuery = true)
    List<Group> findGroupsWithLessOrEqualStudents(@Param("maxStudentCount")long value);
}
