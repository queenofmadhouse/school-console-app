package com.foxminded.chendev.schoolconsoleapp.repository;

import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        GroupRepository.class,
        StudentRepository.class
}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(
        scripts = {"/sql/clear_tables.sql",
                "/sql/users_create.sql",
                "/sql/students_create.sql",
                "/sql/groups_create.sql",
                "/sql/courses_create.sql",
                "/sql/students_courses_relation.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class GroupRepositoryImplTestIT {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void testSaveShouldObjectInDataBase() {

        Group groupMath = Group.builder()
                .withGroupName("Math")
                .build();

        groupRepository.save(groupMath);

        Group foundGroup = groupRepository.findById(1).orElse(null);

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

        groupRepository.save(groupMath);
        groupRepository.save(groupArt);

        List<Group> fourdGroupList = groupRepository.findAll();
        assertNotNull(fourdGroupList);
        assertEquals(2, fourdGroupList.size());
        assertEquals(groupMath.getGroupName(), fourdGroupList.get(0).getGroupName());
        assertEquals(groupArt.getGroupName(), fourdGroupList.get(1).getGroupName());
    }

    @Test
    void saveShouldUpdateName() {

        Group groupMath = Group.builder()
                .withGroupName("Math")
                .build();

        groupRepository.save(groupMath);

        Group foundFgroup = groupRepository.findById(1).orElse(null);

        foundFgroup.setGroupName("Test Group Updated");

        groupRepository.save(foundFgroup);

        Group foundUpdatedGroup = groupRepository.findById(1).orElse(null);

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

        groupRepository.save(groupMath);
        groupRepository.save(groupArt);
        groupRepository.save(groupJava);

        studentRepository.save(studentJohn);
        studentRepository.save(studentJane);
        studentRepository.save(studentTom);
        studentRepository.save(studentAlice);
        studentRepository.save(studentBob);
        studentRepository.save(studentCharlie);
        studentRepository.save(studentEmily);
        studentRepository.save(studentEva);
        studentRepository.save(studentFrank);
        studentRepository.save(studentGrace);

        List<Group> resultList = groupRepository.findGroupsWithLessOrEqualStudents(2);

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

        groupRepository.save(groupMath);
        groupRepository.save(groupArt);
        groupRepository.save(groupJava);

        studentRepository.save(studentJohn);
        studentRepository.save(studentJane);
        studentRepository.save(studentTom);
        studentRepository.save(studentAlice);
        studentRepository.save(studentBob);
        studentRepository.save(studentCharlie);
        studentRepository.save(studentEmily);
        studentRepository.save(studentEva);
        studentRepository.save(studentFrank);
        studentRepository.save(studentGrace);

        List<Group> resultList = groupRepository.findGroupsWithLessOrEqualStudents(valueOfStudentsOnCourse);

        assertTrue(resultList.isEmpty());
    }

    @Test
    void deleteByIdShouldDeleteByIdWhenExists() {

        Group groupA1 = Group.builder()
                .withGroupName("A1")
                .build();

        groupRepository.save(groupA1);

        Group foundGroup = groupRepository.findById(1).orElse(null);

        groupRepository.deleteById(1);

        Group deletedGroup = groupRepository.findById(1).orElse(null);

        assertNotNull(foundGroup);
        assertNull(deletedGroup);
    }

    @Test
    void deleteByIdShouldNotThrowDataBaseRunTimeExceptionWhenNotFound() {

        assertDoesNotThrow(() -> groupRepository.deleteById(100));
    }
}
