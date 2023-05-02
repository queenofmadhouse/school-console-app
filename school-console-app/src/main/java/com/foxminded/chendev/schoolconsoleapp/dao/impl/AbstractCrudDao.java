package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.CrudDao;
import com.foxminded.chendev.schoolconsoleapp.exception.DataBaseRuntimeException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public abstract class AbstractCrudDao<E> implements CrudDao<E> {

    protected final EntityManager entityManager;
    private final String findAllQuery;

    protected AbstractCrudDao(EntityManager entityManager, String findAllQuery) {

        this.entityManager = entityManager;
        this.findAllQuery = findAllQuery;
    }

    @Override
    @Transactional
    public void save(E entity) {
        try {

            entityManager.persist(entity);
        } catch (RuntimeException e) {

            throw new DataBaseRuntimeException("Can't save entity: " + entity, e);
        }
    }

    @Override
    @Transactional
    public Optional<E> findById(long id) {
        try {

            E entityResult = entityManager.find(getEntityClass(), id);

            return Optional.ofNullable(entityResult);
        } catch (RuntimeException e) {
            throw new DataBaseRuntimeException("Can't find by id: " + id, e);
        }
    }

    @Override
    @Transactional
    public List<E> findAll() {
        try {

            List<E> resultList = entityManager.createQuery(findAllQuery, getEntityClass()).getResultList();

            return resultList;
        } catch (RuntimeException e) {
            throw new DataBaseRuntimeException("Can't find all ", e);
        }
    }

    @Override
    @Transactional
    public void update(E entity) {
        try {

            entityManager.merge(entity);
        } catch (RuntimeException e) {

            throw new DataBaseRuntimeException("Can't update entity: " + entity, e);
        }
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        try {

            E entity = entityManager.find(getEntityClass(), id);

            if (entity != null) {
                entityManager.remove(entity);
            }
        } catch (RuntimeException e) {

            throw new DataBaseRuntimeException("Can't delete by id: " + id, e);
        }
    }

    protected abstract Class<E> getEntityClass();
}
