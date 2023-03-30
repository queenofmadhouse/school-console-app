package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.DBConnector;
import com.foxminded.chendev.schoolconsoleapp.dao.DataBaseRuntimeException;
import com.foxminded.chendev.schoolconsoleapp.dao.GroupDao;
import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GroupDaoImpl extends AbstractCrudDao<Group> implements GroupDao {

    private static final Logger logger = LoggerFactory.getLogger(GroupDaoImpl.class);
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

    private final DBConnector connector;

    public GroupDaoImpl(DBConnector connector) {
        super(connector, INSERT_GROUP, SELECT_GROUP_BY_ID, SELECT_ALL_GROUPS, UPDATE_GROUP, DELETE_GROUP_BY_ID);
        this.connector = connector;
    }

    @Override
    protected Group mapResultSetToEntity(ResultSet resultSet) throws SQLException {
        return Group.builder()
                .withGroupId(resultSet.getLong("group_id"))
                .withGroupName(resultSet.getString("group_name"))
                .build();
    }

    @Override
    protected void insert(PreparedStatement preparedStatement, Group entity) throws SQLException {
        preparedStatement.setString(1, entity.getGroupName());
    }

    @Override
    protected void updateValues(PreparedStatement preparedStatement, Group entity) throws SQLException {
        preparedStatement.setString(1, entity.getGroupName());
        preparedStatement.setLong(2, entity.getGroupId());
    }

    @Override
    public List<Group> findGroupsWithLessOrEqualStudents(long value) {
        List<Group> groups = new ArrayList<>();

        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_GROUPS_WITH_LESS_OR_EQUALS_STUDENTS)) {
            preparedStatement.setLong(1, value);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Group group = Group.builder()
                            .withGroupId(resultSet.getLong("group_id"))
                            .withGroupName(resultSet.getString("group_name"))
                            .build();
                    groups.add(group);
                }
            }
        } catch (SQLException e) {
            logger.error("SQLException in findGroupsWithLessOrEqualStudents", e);
            throw new DataBaseRuntimeException("Failed to find groups with less or equal students", e);
        }
        return groups;
    }
}
