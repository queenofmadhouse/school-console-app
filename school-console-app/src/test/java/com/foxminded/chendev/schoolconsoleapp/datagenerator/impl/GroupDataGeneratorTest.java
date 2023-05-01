package com.foxminded.chendev.schoolconsoleapp.datagenerator.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.impl.GroupDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class GroupDataGeneratorTest {
    private static final int AMOUNT_OF_GROUPS = 5;
    private static final String GROUP_NAME_REGEX = "[A-Z]{2}-\\d{2}";

    @Mock
    private GroupDaoImpl groupDao;

    private GroupDataGenerator groupsGenerator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        groupsGenerator = new GroupDataGenerator(groupDao, AMOUNT_OF_GROUPS);
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
        groupsGenerator = new GroupDataGenerator(groupDao, 0);
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
