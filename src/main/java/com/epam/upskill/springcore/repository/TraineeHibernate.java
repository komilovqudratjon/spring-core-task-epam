package com.epam.upskill.springcore.repository;

import com.epam.upskill.springcore.model.Trainee;
import com.epam.upskill.springcore.model.dtos.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * @description: Repository interface for Trainee entity.
 * @date: 08 November 2023 $
 * @time: 5:37 AM 43 $
 * @author: Qudratjon Komilov
 */


@Service
public class TraineeHibernate extends CrudRepository<Trainee, Long> {
    @PersistenceContext
    private EntityManager entityManager;

    public TraineeHibernate() {
        super(Trainee.class);
    }

    public Optional<Trainee> findByUsername(String username) {
        return entityManager.createQuery("select t from Trainee t where t.user.username = :username", Trainee.class)
                .setParameter("username", username)
                .getResultList()
                .stream()
                .findFirst();
    }


    @Transactional
    public void deleteByUsername(String username) {
        Optional<Trainee> username1 = entityManager.createQuery("select t from Trainee t where t.user.username = :username", Trainee.class)
                .setParameter("username", username)
                .getResultList()
                .stream()
                .findFirst();
        username1.ifPresent(trainee -> deleteById(trainee.getId()));
    }

    public Page<Trainee> getByFilter(int page, int size, String search) {
        List<Trainee> search1 = entityManager.createQuery("select t from Trainee t where t.user.firstName like :search OR t.user.lastName like :search OR t.address like :search", Trainee.class)
                .setParameter("search", "%" + search + "%")
                .setFirstResult(page * size)
                .setMaxResults(size)
                .getResultList();
        Long count = entityManager.createQuery("select count(t) from Trainee t where t.user.firstName like :search OR t.user.lastName like :search OR t.address like :search", Long.class)
                .setParameter("search", "%" + search + "%")
                .getSingleResult();
        return new Page<>(search1, page, size, count);
    }

    @Transactional
    public void addTrainers(Long traineeId, Long trainerId) {
        entityManager.createNativeQuery("INSERT INTO trainer_trainee (trainee_id, trainer_id) VALUES (:traineeId, :trainerId)")
                .setParameter("traineeId", traineeId)
                .setParameter("trainerId", trainerId)
                .executeUpdate();
    }
}

