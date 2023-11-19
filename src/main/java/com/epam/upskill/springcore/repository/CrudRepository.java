package com.epam.upskill.springcore.repository;

import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

//@Repository
@Slf4j
public class CrudRepository<T, ID> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<T> entityType;

    public CrudRepository(Class<T> entityType) {
        this.entityType = entityType;
    }

    @Transactional
    public T save(T entity) {
//        log.info("Saving entity: {}", entity.getClass().getSimpleName());
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

