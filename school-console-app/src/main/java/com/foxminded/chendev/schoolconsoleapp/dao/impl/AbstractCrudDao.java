package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.CrudDao;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public abstract class AbstractCrudDao<E> implements CrudDao<E> {

    private final JdbcTemplate jdbcTemplate;
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
        jdbcTemplate.update(saveQuery, getSaveParameters(entity));
    }

    @Override
    public E findById(long id) {
        return jdbcTemplate.queryForObject(findByIdQuery, getRowMapper(), id);
    }

    @Override
    public List<E> findAll() {
        return jdbcTemplate.query(findAllQuery, getRowMapper());
    }

    @Override
    public void update(E entity) {
        jdbcTemplate.update(updateQuery, getUpdateParameters(entity));
    }

    @Override
    public void deleteByID(long id) {
        jdbcTemplate.update(deleteByIdQuery, id);
    }

    protected abstract RowMapper<E> getRowMapper();

    protected abstract Object[] getUpdateParameters(E entity);

    protected abstract Object[] getSaveParameters(E entity);
}
