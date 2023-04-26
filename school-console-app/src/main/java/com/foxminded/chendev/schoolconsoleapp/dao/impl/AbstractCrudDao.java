package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.CrudDao;
import com.foxminded.chendev.schoolconsoleapp.exception.DataBaseRuntimeException;
import org.slf4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public abstract class AbstractCrudDao<E> implements CrudDao<E> {

    protected final JdbcTemplate jdbcTemplate;
    protected final Logger logger;
    private final String saveQuery;
    private final String findByIdQuery;
    private final String findAllQuery;
    private final String updateQuery;
    private final String deleteByIdQuery;

    protected AbstractCrudDao(JdbcTemplate jdbcTemplate, Logger logger, String saveQuery, String findByIdQuery,
                           String findAllQuery, String updateQuery, String deleteByIdQuery) {

        this.jdbcTemplate = jdbcTemplate;
        this.logger = logger;
        this.saveQuery = saveQuery;
        this.findByIdQuery = findByIdQuery;
        this.findAllQuery = findAllQuery;
        this.updateQuery = updateQuery;
        this.deleteByIdQuery = deleteByIdQuery;
    }

    @Override
    public void save(E entity) {
        try {
            logger.info("Method save was cold with parameters: " + entity);
            jdbcTemplate.update(saveQuery, getSaveParameters(entity));
        } catch (DataAccessException e) {
            logger.error("Exception in method save with parameters: " + entity, e);
            throw new DataBaseRuntimeException("Can't save entity: " + entity, e);
        }
    }

    @Override
    public Optional<E> findById(long id) {
        try {
            logger.info("Method findById was cold with parameters: " + id);
            return Optional.ofNullable(jdbcTemplate.queryForObject(findByIdQuery, getRowMapper(), id));
        } catch (DataAccessException e) {
            logger.error("Exception in method save with parameters: " + id, e);
            return Optional.empty();
        }
    }

    @Override
    public List<E> findAll() {
        try {
            logger.info("Method findAll was cold");
            return jdbcTemplate.query(findAllQuery, getRowMapper());
        } catch (DataAccessException e) {
            logger.error("Exception in method findAll", e);
            throw new DataBaseRuntimeException("Can't find all ", e);
        }
    }

    @Override
    public void update(E entity) {
        try {
            logger.info("Method update was cold with parameters: " + entity);
            jdbcTemplate.update(updateQuery, getUpdateParameters(entity));
        } catch (DataAccessException e) {
            logger.error("Exception in method update with parameters: " + entity, e);
            throw new DataBaseRuntimeException("Can't update entity: " + entity, e);
        }
    }

    @Override
    public void deleteById(long id) {
        try {
            logger.info("Method deleteById was cold with parameters: " + id);
            jdbcTemplate.update(deleteByIdQuery, id);
        } catch (Exception e) {
            logger.error("Exception in method deleteById with parameters: " + id, e);
            throw new DataBaseRuntimeException("Can't delete by id: " + id, e);
        }
    }

    protected abstract RowMapper<E> getRowMapper();

    protected abstract Object[] getUpdateParameters(E entity);

    protected abstract Object[] getSaveParameters(E entity);
}
