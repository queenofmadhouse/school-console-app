package com.foxminded.chendev.schoolconsoleapp.service.impl;

import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import com.foxminded.chendev.schoolconsoleapp.repository.GroupRepository;
import com.foxminded.chendev.schoolconsoleapp.service.GroupService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;

    public GroupServiceImpl(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @Override
    public List<Group> findGroupsWithLessOrEqualStudents(long value) {

        return groupRepository.findGroupsWithLessOrEqualStudents(value);
    }
}
