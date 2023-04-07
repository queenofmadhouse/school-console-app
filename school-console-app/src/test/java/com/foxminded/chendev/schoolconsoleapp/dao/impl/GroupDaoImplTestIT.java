package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.DBConnector;
import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.reader.FileReader;
import com.foxminded.chendev.schoolconsoleapp.reader.SQLFileReader;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GroupDaoImplTestIT {

    private GroupDaoImpl groupDao;
    private Connection connection;
    private DBConnector connector;
    private StudentDaoImpl studentDao;

    @BeforeEach
    public void setUp() throws SQLException, IOException {

        connector = new DBConnector("application-test");

        connection = connector.getConnection();

        FileReader fileReader = new SQLFileReader();

        PreparedStatement preparedStatement = connection.prepareStatement(fileReader.readFile("groups_create.sql"));
        preparedStatement.execute();

        PreparedStatement preparedStatement2 = connection.prepareStatement(fileReader.readFile("students_create.sql"));
        preparedStatement2.execute();

        groupDao = new GroupDaoImpl(connector);

    }

    @AfterEach
    public void tearDown() throws SQLException {
        connection.close();
    }

    @Test
    void testSaveShouldObjectInDataBase() {

        Group group = Group.builder()
                .withGroupName("Test Group")
                .build();

        groupDao.save(group);

        Optional<Group> foundGroup = groupDao.findById(1);
        assertTrue(foundGroup.isPresent());
        assertEquals("Test Group", foundGroup.get().getGroupName());
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
    void deleteByIDShouldDeleteByIDIfExists() {

        Group group1 = Group.builder()
                .withGroupName("Test Group 1")
                .build();

        groupDao.save(group1);

        groupDao.deleteByID(1);

        assertEquals(Optional.empty(), groupDao.findById(1));
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

        studentDao = new StudentDaoImpl(connector);

        Group gerup1 = Group.builder()
                .withGroupName("Group1")
                .build();

        Group gerup2 = Group.builder()
                .withGroupName("Group2")
                .build();

        Group gerup3 = Group.builder()
                .withGroupName("Group3")
                .build();

        Student student1 = Student.builder()
                .withFirstName("John")
                .withLastName("Studentsy")
                .withGroupId(Group.builder().withGroupId(1).build())
                .build();

        Student student2 = Student.builder()
                .withFirstName("Jane")
                .withLastName("Doe")
                .withGroupId(Group.builder().withGroupId(1).build())
                .build();

        Student student3 = Student.builder()
                .withFirstName("Tom")
                .withLastName("Smith")
                .withGroupId(Group.builder().withGroupId(1).build())
                .build();

        Student student4 = Student.builder()
                .withFirstName("Alice")
                .withLastName("Johnson")
                .withGroupId(Group.builder().withGroupId(1).build())
                .build();

        Student student5 = Student.builder()
                .withFirstName("Bob")
                .withLastName("Brown")
                .withGroupId(Group.builder().withGroupId(1).build())
                .build();

        Student student6 = Student.builder()
                .withFirstName("Charlie")
                .withLastName("Green")
                .withGroupId(Group.builder().withGroupId(2).build())
                .build();

        Student student7 = Student.builder()
                .withFirstName("Emily")
                .withLastName("White")
                .withGroupId(Group.builder().withGroupId(2).build())
                .build();

        Student student8 = Student.builder()
                .withFirstName("Eva")
                .withLastName("Black")
                .withGroupId(Group.builder().withGroupId(2).build())
                .build();

        Student student9 = Student.builder()
                .withFirstName("Frank")
                .withLastName("Gray")
                .withGroupId(Group.builder().withGroupId(3).build())
                .build();

        Student student10 = Student.builder()
                .withFirstName("Grace")
                .withLastName("Blue")
                .withGroupId(Group.builder().withGroupId(3).build())
                .build();

        groupDao.save(gerup1);
        groupDao.save(gerup2);
        groupDao.save(gerup3);

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

        studentDao = new StudentDaoImpl(connector);

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
                .withGroupId(Group.builder().withGroupId(1).build())
                .build();

        Student student2 = Student.builder()
                .withFirstName("Jane")
                .withLastName("Doe")
                .withGroupId(Group.builder().withGroupId(1).build())
                .build();

        Student student3 = Student.builder()
                .withFirstName("Tom")
                .withLastName("Smith")
                .withGroupId(Group.builder().withGroupId(1).build())
                .build();

        Student student4 = Student.builder()
                .withFirstName("Alice")
                .withLastName("Johnson")
                .withGroupId(Group.builder().withGroupId(1).build())
                .build();

        Student student5 = Student.builder()
                .withFirstName("Bob")
                .withLastName("Brown")
                .withGroupId(Group.builder().withGroupId(1).build())
                .build();

        Student student6 = Student.builder()
                .withFirstName("Charlie")
                .withLastName("Green")
                .withGroupId(Group.builder().withGroupId(2).build())
                .build();

        Student student7 = Student.builder()
                .withFirstName("Emily")
                .withLastName("White")
                .withGroupId(Group.builder().withGroupId(2).build())
                .build();

        Student student8 = Student.builder()
                .withFirstName("Eva")
                .withLastName("Black")
                .withGroupId(Group.builder().withGroupId(2).build())
                .build();

        Student student9 = Student.builder()
                .withFirstName("Frank")
                .withLastName("Gray")
                .withGroupId(Group.builder().withGroupId(3).build())
                .build();

        Student student10 = Student.builder()
                .withFirstName("Grace")
                .withLastName("Blue")
                .withGroupId(Group.builder().withGroupId(3).build())
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

        Group group1 = Group.builder()
                .withGroupName("Group1")
                .build();

        Group group2 = Group.builder()
                .withGroupName("Group2")
                .build();

        Group group3 = Group.builder()
                .withGroupName("Group3")
                .build();

        groupDao.save(group1);
        groupDao.save(group2);
        groupDao.save(group3);

        groupDao.deleteByID(2);

        assertEquals(Optional.empty(), groupDao.findById(2));
    }

    @Test
    void findByStringParamShouldFindByStringParamAndReturnOptional() {

        String sqlQuery = "Select * FROM school.groups WHERE group_name = ?";

        Group group = Group.builder()
                .withGroupName("Group1")
                .build();

        groupDao.save(group);

        Group foundGroup = groupDao.findByStringParam("Group1", sqlQuery).orElse(null);

        assertEquals(1, foundGroup.getGroupId());
        assertEquals("Group1", foundGroup.getGroupName());
    }
}
