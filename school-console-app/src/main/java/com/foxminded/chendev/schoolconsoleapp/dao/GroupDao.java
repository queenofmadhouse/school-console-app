package com.foxminded.chendev.schoolconsoleapp.dao;

import com.foxminded.chendev.schoolconsoleapp.entity.Group;

import java.util.List;

public interface GroupDao extends CrudDao<Group> {

    List<Group> findGroupsWithLessOrEqualStudents(long value);
}
