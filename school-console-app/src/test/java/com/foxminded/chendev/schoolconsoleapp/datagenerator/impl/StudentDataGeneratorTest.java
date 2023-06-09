package com.foxminded.chendev.schoolconsoleapp.datagenerator.impl;

import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.repository.GroupRepository;
import com.foxminded.chendev.schoolconsoleapp.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StudentDataGeneratorTest {

    private static final int AMOUNT_OF_STUDENTS = 10;
    private StudentRepository studentRepository;
    private GroupRepository groupRepository;
    private StudentDataGenerator studentsGenerator;

    @BeforeEach
    void setUp() {

        studentRepository = mock(StudentRepository.class);
        groupRepository = mock(GroupRepository.class);
        studentsGenerator = new StudentDataGenerator(studentRepository, groupRepository, AMOUNT_OF_STUDENTS);
    }

    @Test
    void generateDataShouldGenerateCorrectNumberOfStudents() {

        List<Group> groups = generateMockGroups(5);

        when(groupRepository.findAll()).thenReturn(groups);

        studentsGenerator.generateData();

        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository, times(AMOUNT_OF_STUDENTS)).save(studentCaptor.capture());

        List<Student> generatedStudents = studentCaptor.getAllValues();
        assertEquals(AMOUNT_OF_STUDENTS, generatedStudents.size());
    }

    @Test
    void generateDataShouldGenerateStudentsWithValidNamesAndGroupIDs() {

        List<Group> groups = generateMockGroups(5);
        when(groupRepository.findAll()).thenReturn(groups);

        studentsGenerator.generateData();

        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository, times(AMOUNT_OF_STUDENTS)).save(studentCaptor.capture());

        List<Student> generatedStudents = studentCaptor.getAllValues();
        assertEquals(AMOUNT_OF_STUDENTS, generatedStudents.size());

        for (Student student : generatedStudents) {

            String firstName = student.getFirstName();
            String lastName = student.getLastName();
            long groupID = student.getGroupId();

            assertNotNull(firstName);
            assertNotNull(lastName);

            assertTrue(firstName.length() > 0);
            assertTrue(lastName.length() > 0);
            assertTrue(groups.stream().anyMatch(g -> g.getGroupId() == groupID));
        }
    }

    @Test
    void generateDataShouldGenerateStudentsWithoutGroupsWhenGroupsAreEmpty() {

        List<Group> groups = Collections.emptyList();
        when(groupRepository.findAll()).thenReturn(groups);

        studentsGenerator.generateData();

        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentRepository, times(AMOUNT_OF_STUDENTS)).save(studentCaptor.capture());

        List<Student> generatedStudents = studentCaptor.getAllValues();
        assertEquals(AMOUNT_OF_STUDENTS, generatedStudents.size());

        for (Student student : generatedStudents) {

            String firstName = student.getFirstName();
            String lastName = student.getLastName();
            long groupID = student.getGroupId();

            assertNotNull(firstName);
            assertNotNull(lastName);

            assertTrue(firstName.length() > 0);
            assertTrue(lastName.length() > 0);
            assertEquals(0, groupID);
        }
    }

    private List<Group> generateMockGroups(int amount) {

        List<Group> groups = new ArrayList<>();

        for (int i = 0; i < amount; i++) {

            Group group = mock(Group.class);

            when(group.getGroupId()).thenReturn((long) i);

            groups.add(group);
        }
        return groups;
    }
}
