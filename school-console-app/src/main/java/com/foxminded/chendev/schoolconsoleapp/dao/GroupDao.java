package com.foxminded.chendev.schoolconsoleapp.dao;

import java.sql.PreparedStatement;
import java.util.List;

import com.foxminded.chendev.schoolconsoleapp.entity.Group;

public interface GroupDao extends CrudDao<Group, Long> {
    
    List<Group> findGroupsWithLessOrEqualStudents(long value);

}
