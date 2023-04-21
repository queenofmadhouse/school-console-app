package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.CrudDao;
import com.foxminded.chendev.schoolconsoleapp.exception.DataBaseRuntimeException;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public abstract class AbstractCrudDao<E> implements CrudDao<E> {

    final JdbcTemplate jdbcTemplate;
    private final String saveQuery;
    private final String findByIdQuery;
    private final String findAllQuery;
    private final String updateQuery;
    private final String deleteByIdQuery;

    protected AbstractCrudDao(JdbcTemplate jdbcTemplate, String saveQuery, String findByIdQuery,
                              String findAllQuery, String updateQuery, String deleteByIdQuery) {
        this.saveQuery = saveQuery;
        this.findByIdQuery = findByIdQuery;
        this.findAllQuery = findAllQuery;
        this.updateQuery = updateQuery;
        this.deleteByIdQuery = deleteByIdQuery;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(E entity) {
        try {
            jdbcTemplate.update(saveQuery, getSaveParameters(entity));
        } catch (DataAccessException e) {
            throw new DataBaseRuntimeException("Can't save entity: " + entity, e);
        }
    }

    @Override
    public Optional<E> findById(long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(findByIdQuery, getRowMapper(), id));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<E> findAll() {
        try {
            return jdbcTemplate.query(findAllQuery, getRowMapper());
        } catch (DataAccessException e) {
            throw new DataBaseRuntimeException("Can't find all ", e);
        }
    }

    @Override
    public void update(E entity) {
        try {
            jdbcTemplate.update(updateQuery, getUpdateParameters(entity));
        } catch (DataAccessException e) {
            throw new DataBaseRuntimeException("Can't update entity: " + entity, e);
        }
    }

    @Override
    public void deleteById(long id) {
        try {
            jdbcTemplate.update(deleteByIdQuery, id);
        } catch (Exception e) {
            throw new DataBaseRuntimeException("Can't delete by id: " + id, e);
        }
    }

    protected abstract RowMapper<E> getRowMapper();

    protected abstract Object[] getUpdateParameters(E entity);

    protected abstract Object[] getSaveParameters(E entity);
}
