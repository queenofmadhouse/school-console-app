package com.foxminded.chendev.schoolconsoleapp.dao.impl;

import com.foxminded.chendev.schoolconsoleapp.dao.CrudDao;
import com.foxminded.chendev.schoolconsoleapp.exception.DataBaseRuntimeException;
import org.slf4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public abstract class AbstractCrudDao<E> implements CrudDao<E> {

    protected final EntityManager entityManager;
    protected final  Logger logger;
    private final String findAllQuery;

    protected AbstractCrudDao(EntityManager entityManager, Logger logger, String findAllQuery) {

        this.entityManager = entityManager;
        this.logger = logger;
        this.findAllQuery = findAllQuery;

    }

    @Override
    @Transactional
    public void save(E entity) {
        try {
            entityManager.persist(entity);
            logger.info("Method save was cold with parameters: " + entity);
        } catch (RuntimeException e) {
            logger.error("Exception in method save with parameters: " + entity, e);
            throw new DataBaseRuntimeException("Can't save entity: " + entity, e);
        }
    }

    @Override
    @Transactional
    public Optional<E> findById(long id) {
        try {
            E entityResult = entityManager.find(getEntityClass(), id);
            logger.info("Method findById was cold with parameters: " + id);
            return Optional.ofNullable(entityResult);
        } catch (RuntimeException e) {
            logger.error("Exception in method save with parameters: " + id, e);
            throw new DataBaseRuntimeException("Can't find by id: " + id, e);
        }
    }

    @Override
    @Transactional
    public List<E> findAll() {
        try {
            List<E> resultList = entityManager.createQuery(findAllQuery, getEntityClass()).getResultList();
            logger.info("Method findAll was cold");
            return resultList;
        } catch (RuntimeException e) {
            logger.error("Exception in method findAll", e);
            throw new DataBaseRuntimeException("Can't find all ", e);
        }
    }

    @Override
//    @Transactional
    public void update(E entity) {
        try {
            entityManager.merge(entity);
            logger.info("Method update was cold with parameters: " + entity);
        } catch (RuntimeException e) {
            logger.error("Exception in method update with parameters: " + entity, e);
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
                logger.info("Method deleteById was called with parameters: " + id);
            } else {
                logger.warn("Entity not found for id: " + id);
            }
        } catch (RuntimeException e) {
            logger.error("Exception in method deleteById with parameters: " + id, e);
            throw new DataBaseRuntimeException("Can't delete by id: " + id, e);
        }
    }

    protected abstract Class<E> getEntityClass();
}
