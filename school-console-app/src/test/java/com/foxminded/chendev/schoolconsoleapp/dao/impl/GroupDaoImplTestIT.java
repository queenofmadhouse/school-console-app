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

        Group groupMath = Group.builder()
                .withGroupName("Math")
                .build();

        groupDao.save(groupMath);

        Group foundGroup = groupDao.findById(1).orElse(null);

        assertNotNull(foundGroup);
        assertEquals(groupMath.getGroupName(), foundGroup.getGroupName());
    }

    @Test
    void findAllShouldFindAllGroupsAndReturnListOfThem() {

        Group groupMath = Group.builder()
                .withGroupName("Math")
                .build();

        Group groupArt = Group.builder()
                .withGroupName("Art")
                .build();

        groupDao.save(groupMath);
        groupDao.save(groupArt);

        List<Group> fourdGroupList = groupDao.findAll();
        assertNotNull(fourdGroupList);
        assertEquals(2, fourdGroupList.size());
        assertEquals(groupMath.getGroupName(), fourdGroupList.get(0).getGroupName());
        assertEquals(groupArt.getGroupName(), fourdGroupList.get(1).getGroupName());
    }

    @Test
    void updateShouldUpdateName() {

        Group groupMath = Group.builder()
                .withGroupName("Math")
                .build();

        groupDao.save(groupMath);

        Group foundFgroup = groupDao.findById(1).orElse(null);

        foundFgroup.setGroupName("Test Group Updated");

        Group foundUpdatedGroup = groupDao.findById(1).orElse(null);

        groupDao.update(foundFgroup);

        assertEquals(foundFgroup.getGroupName(), foundUpdatedGroup.getGroupName());


    }

    @Test
    void findGroupsWithLessOrEqualStudentsShouldListOfGroupsWhenInputValid() {

        Group groupMath = Group.builder()
                .withGroupName("Math")
                .build();

        Group groupArt = Group.builder()
                .withGroupName("Art")
                .build();

        Group groupJava = Group.builder()
                .withGroupName("Java")
                .build();

        Student studentJohn = Student.builder()
                .withFirstName("John")
                .withLastName("Studentsy")
                .withGroupId(1)
                .build();

        Student studentJane = Student.builder()
                .withFirstName("Jane")
                .withLastName("Doe")
                .withGroupId(1)
                .build();

        Student studentTom = Student.builder()
                .withFirstName("Tom")
                .withLastName("Smith")
                .withGroupId(1)
                .build();

        Student studentAlice = Student.builder()
                .withFirstName("Alice")
                .withLastName("Johnson")
                .withGroupId(1)
                .build();

        Student studentBob = Student.builder()
                .withFirstName("Bob")
                .withLastName("Brown")
                .withGroupId(1)
                .build();

        Student studentCharlie = Student.builder()
                .withFirstName("Charlie")
                .withLastName("Green")
                .withGroupId(2)
                .build();

        Student studentEmily = Student.builder()
                .withFirstName("Emily")
                .withLastName("White")
                .withGroupId(2)
                .build();

        Student studentEva = Student.builder()
                .withFirstName("Eva")
                .withLastName("Black")
                .withGroupId(2)
                .build();

        Student studentFrank = Student.builder()
                .withFirstName("Frank")
                .withLastName("Gray")
                .withGroupId(3)
                .build();

        Student studentGrace = Student.builder()
                .withFirstName("Grace")
                .withLastName("Blue")
                .withGroupId(3)
                .build();

        int valueOfStudentsOnCourse = 1;

        groupDao.save(groupMath);
        groupDao.save(groupArt);
        groupDao.save(groupJava);

        studentDao.save(studentJohn);
        studentDao.save(studentJane);
        studentDao.save(studentTom);
        studentDao.save(studentAlice);
        studentDao.save(studentBob);
        studentDao.save(studentCharlie);
        studentDao.save(studentEmily);
        studentDao.save(studentEva);
        studentDao.save(studentFrank);
        studentDao.save(studentGrace);

        List<Group> resultList = groupDao.findGroupsWithLessOrEqualStudents(2);

        assertEquals(1, resultList.size());
        assertEquals("Java", resultList.get(0).getGroupName());

    }

    @Test
    void findGroupsWithLessOrEqualStudentsShouldReturnEmptyListWhenNotFound() {

        Group groupMath = Group.builder()
                .withGroupName("Math")
                .build();

        Group groupArt = Group.builder()
                .withGroupName("Art")
                .build();

        Group groupJava = Group.builder()
                .withGroupName("Java")
                .build();

        Student studentJohn = Student.builder()
                .withFirstName("John")
                .withLastName("Studentsy")
                .withGroupId(1)
                .build();

        Student studentJane = Student.builder()
                .withFirstName("Jane")
                .withLastName("Doe")
                .withGroupId(1)
                .build();

        Student studentTom = Student.builder()
                .withFirstName("Tom")
                .withLastName("Smith")
                .withGroupId(1)
                .build();

        Student studentAlice = Student.builder()
                .withFirstName("Alice")
                .withLastName("Johnson")
                .withGroupId(1)
                .build();

        Student studentBob = Student.builder()
                .withFirstName("Bob")
                .withLastName("Brown")
                .withGroupId(1)
                .build();

        Student studentCharlie = Student.builder()
                .withFirstName("Charlie")
                .withLastName("Green")
                .withGroupId(2)
                .build();

        Student studentEmily = Student.builder()
                .withFirstName("Emily")
                .withLastName("White")
                .withGroupId(2)
                .build();

        Student studentEva = Student.builder()
                .withFirstName("Eva")
                .withLastName("Black")
                .withGroupId(2)
                .build();

        Student studentFrank = Student.builder()
                .withFirstName("Frank")
                .withLastName("Gray")
                .withGroupId(3)
                .build();

        Student studentGrace = Student.builder()
                .withFirstName("Grace")
                .withLastName("Blue")
                .withGroupId(3)
                .build();

        int valueOfStudentsOnCourse = 1;

        groupDao.save(groupMath);
        groupDao.save(groupArt);
        groupDao.save(groupJava);

        studentDao.save(studentJohn);
        studentDao.save(studentJane);
        studentDao.save(studentTom);
        studentDao.save(studentAlice);
        studentDao.save(studentBob);
        studentDao.save(studentCharlie);
        studentDao.save(studentEmily);
        studentDao.save(studentEva);
        studentDao.save(studentFrank);
        studentDao.save(studentGrace);

        List<Group> resultList = groupDao.findGroupsWithLessOrEqualStudents(valueOfStudentsOnCourse);

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
