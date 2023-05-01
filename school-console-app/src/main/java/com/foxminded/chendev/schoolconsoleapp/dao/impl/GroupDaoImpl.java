package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.GroupDao;
import com.foxminded.chendev.schoolconsoleapp.entity.Group;
import com.foxminded.chendev.schoolconsoleapp.exception.DataBaseRuntimeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class GroupDaoImpl extends AbstractCrudDao<Group> implements GroupDao {

    private static final String SELECT_ALL_GROUPS = "SELECT g FROM Group g";
    private static final String SELECT_GROUPS_WITH_LESS_OR_EQUALS_STUDENTS = "SELECT g.group_id, g.group_name, " +
            "COUNT(s.user_id) as student_count FROM school.groups g " +
            "LEFT JOIN school.students s ON g.group_id = s.group_id " +
            "GROUP BY g.group_id, g.group_name " +
            "HAVING COUNT(s.user_id) <= :maxStudentCount";

    private static final Logger logger = LoggerFactory.getLogger(GroupDaoImpl.class);

    public GroupDaoImpl(EntityManager entityManager) {
        super(entityManager, logger,SELECT_ALL_GROUPS);
    }

    @Override
    @Transactional
    public List<Group> findGroupsWithLessOrEqualStudents(long value) {
        try {

            List<Group> groupList = entityManager.createNativeQuery(SELECT_GROUPS_WITH_LESS_OR_EQUALS_STUDENTS, Group.class)
                    .setParameter("maxStudentCount", value)
                    .getResultList();

            logger.info("Method findGroupsWithLessOrEqualStudents was cold with parameters: " + value);

            return groupList;
        } catch (RuntimeException e) {
            logger.error("Exception in method findGroupsWithLessOrEqualStudents with parameters: " + value, e);
            throw new DataBaseRuntimeException("Can't find groups with less or equals students: " + value, e);
        }
    }

    @Override
    protected Class<Group> getEntityClass() {
        return Group.class;
    }
}
