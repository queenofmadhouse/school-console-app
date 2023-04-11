package com.foxminded.chendev.schoolconsoleapp.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<E> {

    void save(E entity);

    E findById(long id);

    List<E> findAll();

    void update(E entity);

    void deleteByID(long id);
}
