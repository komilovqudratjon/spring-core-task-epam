package com.epam.upskill.springcore.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

public class CrudRepository<T, ID> {

    @PersistenceContext
    private  EntityManager entityManager;

    private final Class<T> entityType;

    public CrudRepository( Class<T> entityType) {
        this.entityType = entityType;
    }

    public T save(T entity) {
        return entityManager.merge(entity);
    }

    public Optional<T> findById(ID id) {
        T entity = entityManager.find(entityType, id);
        return Optional.ofNullable(entity);
    }

    public void deleteById(ID id) {
        T entity = entityManager.find(entityType, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    public List<T> findAll() {
        String query = "SELECT e FROM " + entityType.getName() + " e";
        return entityManager.createQuery(query, entityType).getResultList();
    }
}

