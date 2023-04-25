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
    StudentDaoImpl studentDao;

    @MockBean
    GroupDaoImpl groupDao;

    @Autowired
    GroupServiceImpl groupService;

    @Test
    void findGroupsWithLessOrEqualStudentsShouldReturnListOfGroups() {

        Group group = Group.builder()
                .withGroupName("Art")
                .build();

        List<Group> groupList = new ArrayList<>();
        groupList.add(group);

        when(groupDao.findGroupsWithLessOrEqualStudents(1)).thenReturn(groupList);

        List<Group> foundGroupList = groupService.findGroupsWithLessOrEqualStudents(1);

        assertNotNull(foundGroupList);
        assertEquals(1, foundGroupList.size());
        assertEquals("Art", foundGroupList.get(0).getGroupName());

        verify(groupDao).findGroupsWithLessOrEqualStudents(1);
    }
}
