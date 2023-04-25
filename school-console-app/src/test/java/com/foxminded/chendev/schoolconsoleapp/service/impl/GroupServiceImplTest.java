package com.foxminded.chendev.schoolconsoleapp.service.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.impl.GroupDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.dao.impl.StudentDaoImpl;
import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class GroupServiceImplTest {

    @MockBean
    private StudentDaoImpl studentDao;

    @MockBean
    private GroupDaoImpl groupDao;

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

        when(groupDao.findGroupsWithLessOrEqualStudents(valueOfStudents)).thenReturn(groupList);

        List<Group> foundGroupList = groupService.findGroupsWithLessOrEqualStudents(valueOfStudents);

        assertNotNull(foundGroupList);
        assertEquals(1, foundGroupList.size());
        assertEquals("Art", foundGroupList.get(0).getGroupName());

        verify(groupDao).findGroupsWithLessOrEqualStudents(valueOfStudents);
    }
}
