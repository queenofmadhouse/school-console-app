package com.foxminded.chendev.schoolconsoleapp.service;

import com.foxminded.chendev.schoolconsoleapp.entity.Group;

import java.util.List;

public interface GroupService {

    List<Group> findGroupsWithLessOrEqualStudents(long value);
}
