package com.foxminded.chendev.schoolconsoleapp.generator.datagenerator;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.foxminded.chendev.schoolconsoleapp.dao.impl.GroupDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import java.util.ArrayList;
import java.util.List;

import com.foxminded.chendev.schoolconsoleapp.generator.datagenegator.GroupsGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class GroupsGeneratorTest {

    private static final int AMOUNT_OF_GROUPS = 5;
    private static final String GROUP_NAME_REGEX = "[A-Z]{2}-\\d{2}";

    @Mock
    private GroupDaoImpl groupDao;

    private GroupsGenerator groupsGenerator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        groupsGenerator = new GroupsGenerator(groupDao, AMOUNT_OF_GROUPS);
    }

    @Test
    void generateData_shouldGenerateCorrectNumberOfGroups() {
        groupsGenerator.generateData();

        ArgumentCaptor<Group> groupCaptor = ArgumentCaptor.forClass(Group.class);
        verify(groupDao, times(AMOUNT_OF_GROUPS)).save(groupCaptor.capture());

        List<Group> generatedGroups = groupCaptor.getAllValues();
        assertEquals(AMOUNT_OF_GROUPS, generatedGroups.size());

        for (Group group : generatedGroups) {
            String groupName = group.getGroupName();
            assertTrue(groupName.matches(GROUP_NAME_REGEX));
        }
    }

    @Test
    void generateData_shouldNotGenerateGroupsIfAmountIsZero() {
        groupsGenerator = new GroupsGenerator(groupDao, 0);
        groupsGenerator.generateData();

        verify(groupDao, never()).save(any());
    }

    @Test
    void generateData_shouldGenerateGroupsWithUniqueNames() {
        groupsGenerator.generateData();

        ArgumentCaptor<Group> groupCaptor = ArgumentCaptor.forClass(Group.class);
        verify(groupDao, times(AMOUNT_OF_GROUPS)).save(groupCaptor.capture());

        List<Group> generatedGroups = groupCaptor.getAllValues();
        assertEquals(AMOUNT_OF_GROUPS, generatedGroups.size());

        List<String> groupNames = new ArrayList<>();
        for (Group group : generatedGroups) {
            String groupName = group.getGroupName();
            assertTrue(groupName.matches(GROUP_NAME_REGEX));
            assertFalse(groupNames.contains(groupName));
            groupNames.add(groupName);
        }
    }
}
