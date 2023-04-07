package com.foxminded.chendev.schoolconsoleapp.generator.datagenerator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.foxminded.chendev.schoolconsoleapp.dao.impl.GroupDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.dao.impl.StudentDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.foxminded.chendev.schoolconsoleapp.generator.datagenegator.StudentsGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class StudentsGeneratorTest {

    private static final int AMOUNT_OF_STUDENTS = 10;

    private StudentDaoImpl studentDao;
    private GroupDaoImpl groupDao;
    private StudentsGenerator studentsGenerator;

    @BeforeEach
    void setUp() {
        studentDao = mock(StudentDaoImpl.class);
        groupDao = mock(GroupDaoImpl.class);
        studentsGenerator = new StudentsGenerator(studentDao, groupDao, AMOUNT_OF_STUDENTS);
    }

    @Test
    void generateData_shouldGenerateCorrectNumberOfStudents() {
        List<Group> groups = generateMockGroups(5);
        when(groupDao.findAll()).thenReturn(groups);

        studentsGenerator.generateData();

        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentDao, times(AMOUNT_OF_STUDENTS)).save(studentCaptor.capture());

        List<Student> generatedStudents = studentCaptor.getAllValues();
        assertEquals(AMOUNT_OF_STUDENTS, generatedStudents.size());
    }

    @Test
    void generateData_shouldGenerateStudentsWithValidNamesAndGroupIDs() {
        List<Group> groups = generateMockGroups(5);
        when(groupDao.findAll()).thenReturn(groups);

        studentsGenerator.generateData();

        ArgumentCaptor<Student> studentCaptor = ArgumentCaptor.forClass(Student.class);
        verify(studentDao, times(AMOUNT_OF_STUDENTS)).save(studentCaptor.capture());

        List<Student> generatedStudents = studentCaptor.getAllValues();
        assertEquals(AMOUNT_OF_STUDENTS, generatedStudents.size());

        for (Student student : generatedStudents) {
            String firstName = student.getFirstName();
            String lastName = student.getLastName();
            long groupID = student.getGroupID();
            assertNotNull(firstName);
            assertNotNull(lastName);
            assertTrue(firstName.length() > 0);
            assertTrue(lastName.length() > 0);
            assertTrue(groups.stream().anyMatch(g -> g.getGroupID() == groupID));
        }
    }

    private List<Group> generateMockGroups(int amount) {
        List<Group> groups = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            Group group = mock(Group.class);
            when(group.getGroupID()).thenReturn((long) i);
            groups.add(group);
        }
        return groups;
    }
}

