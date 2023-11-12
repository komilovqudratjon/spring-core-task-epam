package com.epam.upskill.springcore.service.db;

import java.util.List;
import java.util.Optional;

/**
 * GenericDatabase provides a CRUD interface for entity classes.
 * This interface can be extended by any entity-specific repository interface
 * to provide basic database operations for that entity.
 *
 * @param <T>  the type of the entity
 * @param <ID> the type of the entity's identifier
 * @author: Qudratjon Komilov
 * @date: 09 November 2023
 * @time: 5:21 PM 15
 */
public interface GenericDatabase<T, ID> {
    T save(T entity);

    Optional<T> findById(ID id);

    void deleteById(ID id);

    List<T> findAll();
}

