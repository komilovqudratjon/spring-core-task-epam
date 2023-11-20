package com.epam.upskill.springcore.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public class CrudRepository<T, ID> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<T> entityType;

    public CrudRepository(Class<T> entityType) {
        this.entityType = entityType;
    }

    /**
     * Save or update entity
     *
     * @param entity - entity to save
     * @return saved entity
     */
    @Transactional
    public T save(T entity) {
        return entityManager.merge(entity);
    }


    /**
     * Find entity by id
     *
     * @param id
     * @return entity
     */
    public Optional<T> findById(ID id) {
        T entity = entityManager.find(entityType, id);
        return Optional.ofNullable(entity);
    }

    /**
     * Delete entity by id
     *
     * @param id
     */
    @Transactional
    public void deleteById(ID id) {
        T entity = entityManager.find(entityType, id);
        if (entity != null) {
            entityManager.remove(entity);
        }
    }

    /**
     * Find all entities
     *
     * @return list of entities
     */
    public List<T> findAll() {
        String query = "SELECT e FROM " + entityType.getName() + " e";
        return entityManager.createQuery(query, entityType).getResultList();
    }
}

