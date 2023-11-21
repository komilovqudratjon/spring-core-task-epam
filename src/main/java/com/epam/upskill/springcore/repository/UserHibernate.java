package com.epam.upskill.springcore.repository;

import com.epam.upskill.springcore.model.Users;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

/**
 * @description: Repository interface for Users entity.
 * @date: 08 November 2023 $
 * @time: 5:37 AM 10 $
 * @author: Qudratjon Komilov
 */


@Service
@Slf4j
public class UserHibernate extends CrudRepository<Users, Long> {


    @PersistenceContext
    private EntityManager entityManager;

    public UserHibernate() {
        super(Users.class);
    }

    public boolean existsByUsername(String username) {
        log.trace("existsByUsername({})", username);
        return entityManager.createQuery("SELECT COUNT(u) FROM Users u WHERE u.username = :username", Long.class)
                .setParameter("username", username)
                .getSingleResult() > 0;
    }


    public Optional<Users> findByUsername(String username) {
        log.trace("findByUsername({})", username);
        return entityManager.createQuery("SELECT u FROM Users u WHERE u.username = :username", Users.class)
                .setParameter("username", username)
                .getResultList()
                .stream()
                .findFirst();
    }
}
