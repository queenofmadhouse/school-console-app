package com.foxminded.chendev.schoolconsoleapp.service.impl;

import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import com.foxminded.chendev.schoolconsoleapp.exception.DataBaseRuntimeException;
import com.foxminded.chendev.schoolconsoleapp.repository.GroupRepository;
import com.foxminded.chendev.schoolconsoleapp.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class GroupServiceImplTest {

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private GroupRepository groupRepository;

    @Autowired
    private GroupServiceImpl groupService;

    @Test
    void findGroupsWithLessOrEqualStudentsShouldReturnListOfGroups() {

        Group groupArt = Group.builder()
                .withGroupName("Art")
                .build();

        List<Group> groupList = new ArrayList<>();
        groupList.add(groupArt);
        int valueOfStudents = 1;

        when(groupRepository.findGroupsWithLessOrEqualStudents(valueOfStudents)).thenReturn(groupList);

        List<Group> foundGroupList = groupService.findGroupsWithLessOrEqualStudents(valueOfStudents);

        assertNotNull(foundGroupList);
        assertEquals(1, foundGroupList.size());
        assertEquals("Art", foundGroupList.get(0).getGroupName());

        verify(groupRepository).findGroupsWithLessOrEqualStudents(valueOfStudents);
    }
}
