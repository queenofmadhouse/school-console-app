package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
@Sql(
        scripts = {"/sql/clear_tables.sql",
                "/sql/users_create.sql",
                "/sql/students_create.sql",
                "/sql/groups_create.sql",
                "/sql/courses_create.sql",
                "/sql/students_courses_relation.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class GroupDaoImplTestIT {

    @Autowired
    private GroupDaoImpl groupDao;

    @Autowired
    private StudentDaoImpl studentDao;

    @Test
    void testSaveShouldObjectInDataBase() {

        Group group = Group.builder()
                .withGroupName("Test Group")
                .build();

        groupDao.save(group);

        Group foundGroup = groupDao.findById(1).orElse(null);

        assertNotNull(foundGroup);
        assertEquals("Test Group", foundGroup.getGroupName());
    }

    @Test
    void findAllShouldFindAllGroupsAndReturnListOfThem() {

        Group group1 = Group.builder()
                .withGroupName("Test Group 1")
                .build();

        Group group2 = Group.builder()
                .withGroupName("Test Group 2")
                .build();

        groupDao.save(group1);
        groupDao.save(group2);

        List<Group> groups = groupDao.findAll();
        assertNotNull(groups);
        assertEquals(2, groups.size());
        assertEquals("Test Group 1", groups.get(0).getGroupName());
        assertEquals("Test Group 2", groups.get(1).getGroupName());
    }

    @Test
    void updateShouldUpdateName() {

        Group group1 = Group.builder()
                .withGroupName("Test Group 1")
                .build();

        groupDao.save(group1);

        Group group = groupDao.findById(1).orElse(null);

        group.setGroupName("Test Group Updated");


        groupDao.update(group);

        assertEquals("Test Group Updated", groupDao.findById(1).orElse(null).getGroupName());


    }

    @Test
    void findGroupsWithLessOrEqualStudentsShouldListOfGroupsWhenInputValid() {

        Group group1 = Group.builder()
                .withGroupName("Group1")
                .build();

        Group group2 = Group.builder()
                .withGroupName("Group2")
                .build();

        Group group3 = Group.builder()
                .withGroupName("Group3")
                .build();

        Student student1 = Student.builder()
                .withFirstName("John")
                .withLastName("Studentsy")
                .withGroupId(1)
                .build();

        Student student2 = Student.builder()
                .withFirstName("Jane")
                .withLastName("Doe")
                .withGroupId(1)
                .build();

        Student student3 = Student.builder()
                .withFirstName("Tom")
                .withLastName("Smith")
                .withGroupId(1)
                .build();

        Student student4 = Student.builder()
                .withFirstName("Alice")
                .withLastName("Johnson")
                .withGroupId(1)
                .build();

        Student student5 = Student.builder()
                .withFirstName("Bob")
                .withLastName("Brown")
                .withGroupId(1)
                .build();

        Student student6 = Student.builder()
                .withFirstName("Charlie")
                .withLastName("Green")
                .withGroupId(2)
                .build();

        Student student7 = Student.builder()
                .withFirstName("Emily")
                .withLastName("White")
                .withGroupId(2)
                .build();

        Student student8 = Student.builder()
                .withFirstName("Eva")
                .withLastName("Black")
                .withGroupId(2)
                .build();

        Student student9 = Student.builder()
                .withFirstName("Frank")
                .withLastName("Gray")
                .withGroupId(3)
                .build();

        Student student10 = Student.builder()
                .withFirstName("Grace")
                .withLastName("Blue")
                .withGroupId(3)
                .build();

        groupDao.save(group1);
        groupDao.save(group2);
        groupDao.save(group3);

        studentDao.save(student1);
        studentDao.save(student2);
        studentDao.save(student3);
        studentDao.save(student4);
        studentDao.save(student5);
        studentDao.save(student6);
        studentDao.save(student7);
        studentDao.save(student8);
        studentDao.save(student9);
        studentDao.save(student10);

        List<Group> resultList = groupDao.findGroupsWithLessOrEqualStudents(2);

        assertEquals(1, resultList.size());
        assertEquals("Group3", resultList.get(0).getGroupName());

    }

    @Test
    void findGroupsWithLessOrEqualStudentsShouldReturnEmptyListWhenNotFound() {

        Group group1 = Group.builder()
                .withGroupName("Group1")
                .build();

        Group group2 = Group.builder()
                .withGroupName("Group2")
                .build();

        Group group3 = Group.builder()
                .withGroupName("Group3")
                .build();

        Student student1 = Student.builder()
                .withFirstName("John")
                .withLastName("Studentsy")
                .withGroupId(1)
                .build();

        Student student2 = Student.builder()
                .withFirstName("Jane")
                .withLastName("Doe")
                .withGroupId(1)
                .build();

        Student student3 = Student.builder()
                .withFirstName("Tom")
                .withLastName("Smith")
                .withGroupId(1)
                .build();

        Student student4 = Student.builder()
                .withFirstName("Alice")
                .withLastName("Johnson")
                .withGroupId(1)
                .build();

        Student student5 = Student.builder()
                .withFirstName("Bob")
                .withLastName("Brown")
                .withGroupId(1)
                .build();

        Student student6 = Student.builder()
                .withFirstName("Charlie")
                .withLastName("Green")
                .withGroupId(2)
                .build();

        Student student7 = Student.builder()
                .withFirstName("Emily")
                .withLastName("White")
                .withGroupId(2)
                .build();

        Student student8 = Student.builder()
                .withFirstName("Eva")
                .withLastName("Black")
                .withGroupId(2)
                .build();

        Student student9 = Student.builder()
                .withFirstName("Frank")
                .withLastName("Gray")
                .withGroupId(3)
                .build();

        Student student10 = Student.builder()
                .withFirstName("Grace")
                .withLastName("Blue")
                .withGroupId(3)
                .build();

        groupDao.save(group1);
        groupDao.save(group2);
        groupDao.save(group3);

        studentDao.save(student1);
        studentDao.save(student2);
        studentDao.save(student3);
        studentDao.save(student4);
        studentDao.save(student5);
        studentDao.save(student6);
        studentDao.save(student7);
        studentDao.save(student8);
        studentDao.save(student9);
        studentDao.save(student10);

        List<Group> resultList = groupDao.findGroupsWithLessOrEqualStudents(1);

        assertTrue(resultList.isEmpty());
    }

    @Test
    void deleteByIDShouldDeleteByID() {

        Group group = Group.builder()
                .withGroupName("Group1")
                .build();

        groupDao.save(group);

        groupDao.deleteById(1);

        Optional <Group> optionalGroup = groupDao.findById(1);

        assertFalse(optionalGroup.isPresent());
    }
}
