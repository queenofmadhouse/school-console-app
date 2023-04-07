package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;

import com.foxminded.chendev.schoolconsoleapp.dao.CrudDao;
import com.foxminded.chendev.schoolconsoleapp.dao.DBConnector;
import com.foxminded.chendev.schoolconsoleapp.dao.DataBaseRuntimeException;

public abstract class AbstractCrudDao<E> implements CrudDao<E, Long> {

    private final DBConnector connector;
    private final String saveQuery;
    private final String findByIdQuery;
    private final String findAllQuery;
    private final String updateQuery;
    private final String deleteByIdQuery;

    protected static final BiConsumer<PreparedStatement, String> STING_CONSUMER
            = (PreparedStatement pr, String param) -> {
        try {
            pr.setString(1, param);
        } catch (SQLException e1) {
            throw new DataBaseRuntimeException(e1);
        }
    };

    protected static final BiConsumer<PreparedStatement, Long> LONG_CONSUMER
            = (PreparedStatement pr, Long param) -> {
        try {
            pr.setLong(1, param);
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    };

    public AbstractCrudDao(DBConnector connector, String saveQuery, String findByIdQuery,
                           String findAllQuery, String updateQuery, String deleteByIdQuery) {
        this.connector = connector;
        this.saveQuery = saveQuery;
        this.findByIdQuery = findByIdQuery;
        this.findAllQuery = findAllQuery;
        this.updateQuery = updateQuery;
        this.deleteByIdQuery = deleteByIdQuery;

    }

    @Override
    public void save(E entity) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(saveQuery)) {
            insert(preparedStatement, entity);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new DataBaseRuntimeException("Insertion is failed", e);
        }
    }

    @Override
    public Optional<E> findById(long id) {
        return findByLongParam(id, findByIdQuery);
    }

    @Override
    public List<E> findAll() {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findAllQuery);
             final ResultSet resultSet = preparedStatement.executeQuery();) {

            List<E> entities = new ArrayList<>();
            while (resultSet.next()) {
                entities.add(mapResultSetToEntity(resultSet));
            }

            return entities;
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    public void update(E entity) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
             updateValues(preparedStatement, entity);

             preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }

    @Override
    public void deleteByID(long ID) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(deleteByIdQuery)) {
            preparedStatement.setLong(1, ID);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }

    protected Optional<E> findByLongParam(long id, String query) {
        return findByParam(id, query, LONG_CONSUMER);
    }

    protected Optional<E> findByStringParam(String param, String query) {
        return findByParam(param, query, STING_CONSUMER);
    }

    private <P> Optional<E> findByParam(P param, String query, BiConsumer<PreparedStatement, P> consumer) {
        try (Connection connection = connector.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            consumer.accept(preparedStatement, param);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next() ? Optional.ofNullable(mapResultSetToEntity(resultSet)) : Optional.empty();
            }
        } catch (SQLException e) {
            throw new DataBaseRuntimeException(e);
        }
    }

    protected abstract E mapResultSetToEntity(ResultSet resultSet) throws SQLException;

    protected abstract void insert(PreparedStatement preparedStatement, E entity) throws SQLException;

    protected abstract void updateValues(PreparedStatement preparedStatement, E entity) throws SQLException;
}
