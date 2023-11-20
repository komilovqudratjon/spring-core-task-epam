package com.epam.upskill.springcore.repository;

import com.epam.upskill.springcore.model.Trainer;
import com.epam.upskill.springcore.model.dtos.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TrainerHibernate extends CrudRepository<Trainer, Long> {
    @PersistenceContext
    private EntityManager entityManager;

    public TrainerHibernate() {
        super(Trainer.class);
    }

    /**
     * Finds a Trainer by its username.
     *
     * @param username the username of the Trainer
     * @return an Optional containing the found Trainer or empty if not found
     */
    public Optional<Trainer> findByUserUsername(String username) {
        return entityManager
                .createQuery("SELECT t FROM Trainer t WHERE t.user.username = :username", Trainer.class)
                .setParameter("username", username)
                .getResultList().stream().findFirst();
    }

    public Page<Trainer> getByFilter(int page, int size, String search) {
        List<Trainer> search1 = entityManager.createQuery("select t from Trainer t where t.user.firstName like :search OR t.user.lastName like :search", Trainer.class)
                .setParameter("search", "%" + search + "%")
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
        Long count = entityManager.createQuery("select count(t) from Trainer t where t.user.firstName like :search OR t.user.lastName like :search", Long.class)
                .setParameter("search", "%" + search + "%")
                .getSingleResult();
        return new Page<>(search1, page, size, count);
    }

    public Page<Trainer> getNotAssignedTrainers(Long traineeId) {
        List<Trainer> search1 = entityManager.createQuery("select t from Trainer t where t.id not in (select t.id from Trainer t join t.trainees tr where tr.id = :traineeId)", Trainer.class)
                .setParameter("traineeId", traineeId)
                .getResultList();
        Long count = entityManager.createQuery("select count(t) from Trainer t where t.id not in (select t.id from Trainer t join t.trainees tr where tr.id = :traineeId)", Long.class)
                .setParameter("traineeId", traineeId)
                .getSingleResult();
        return new Page<>(search1, 0, 0, count);
    }
}