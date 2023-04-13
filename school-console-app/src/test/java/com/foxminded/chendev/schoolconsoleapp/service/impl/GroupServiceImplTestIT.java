package com.foxminded.chendev.schoolconsoleapp.service.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.GroupDao;
import com.foxminded.chendev.schoolconsoleapp.dao.StudentDao;
import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import com.foxminded.chendev.schoolconsoleapp.entity.Student;
import com.foxminded.chendev.schoolconsoleapp.service.GroupService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@Sql(
        scripts = {"/sql/clear_tables.sql",
                "/sql/students_create.sql",
                "/sql/groups_create.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD
)
class GroupServiceImplTestIT {

    @Autowired
    GroupService groupService;

    @Autowired
    StudentDao studentDao;

    @Autowired
    GroupDao groupDao;

    @Test
    void findGroupsWithLessOrEqualStudentsShouldReturnListOfGroups() {

        Student student1 = Student.builder()
                .withFirstName("Alex")
                .withLastName("Kapranos")
                .withGroupId(1)
                .build();

        Student student2 = Student.builder()
                .withFirstName("Nikol")
                .withLastName("Smith")
                .withGroupId(1)
                .build();

        Student student3 = Student.builder()
                .withFirstName("Jane")
                .withLastName("Kamala")
                .withGroupId(2)
                .build();

        Group group1 = Group.builder()
                .withGroupName("Math")
                .build();

        Group group2 = Group.builder()
                .withGroupName("Art")
                .build();

        studentDao.save(student1);
        studentDao.save(student2);
        studentDao.save(student3);

        groupDao.save(group1);
        groupDao.save(group2);

        List<Group> groupList = groupService.findGroupsWithLessOrEqualStudents(1);

        assertNotNull(groupList);
        assertEquals(1, groupList.size());
        assertEquals("Art", groupList.get(0).getGroupName());
    }
}
