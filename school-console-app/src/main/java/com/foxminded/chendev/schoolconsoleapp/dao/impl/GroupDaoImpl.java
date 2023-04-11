package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.GroupDao;
import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupDaoImpl extends AbstractCrudDao<Group> implements GroupDao {

    private static final String INSERT_GROUP = "INSERT INTO school.groups (group_name) VALUES (?)";
    private static final String SELECT_GROUP_BY_ID = "SELECT * FROM school.groups WHERE group_id = ?";
    private static final String SELECT_ALL_GROUPS = "SELECT * FROM school.groups";
    private static final String UPDATE_GROUP = "UPDATE school.groups SET group_name = ? WHERE group_id = ?";
    private static final String DELETE_GROUP_BY_ID = "DELETE FROM school.groups WHERE group_id = ?";
    private static final String SELECT_GROUPS_WITH_LESS_OR_EQUALS_STUDENTS = "SELECT g.group_id, g.group_name," +
            "COUNT(s.student_id) as student_count FROM school.groups g " +
            "LEFT JOIN school.students s ON g.group_id = s.group_id " +
            "GROUP BY g.group_id, g.group_name " +
            "HAVING COUNT(s.student_id) <= ?";

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public GroupDaoImpl(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, INSERT_GROUP, SELECT_GROUP_BY_ID, SELECT_ALL_GROUPS, UPDATE_GROUP, DELETE_GROUP_BY_ID);
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Group> findGroupsWithLessOrEqualStudents(long value) {
        return jdbcTemplate.query(SELECT_GROUPS_WITH_LESS_OR_EQUALS_STUDENTS, new Object[]{value}, getRowMapper());
    }

    @Override
    protected RowMapper<Group> getRowMapper() {
        return (resultSet, rowNum) -> {

            return Group.builder()
                    .withGroupId(resultSet.getLong("group_id"))
                    .withGroupName(resultSet.getString("group_name"))
                    .build();

        };
    }

    @Override
    protected Object[] getUpdateParameters(Group entity) {
        return new Object[]{entity.getGroupName(), entity.getGroupId()};
    }

    @Override
    protected Object[] getSaveParameters(Group entity) {
        return new Object[]{entity.getGroupName()};
    }
}
