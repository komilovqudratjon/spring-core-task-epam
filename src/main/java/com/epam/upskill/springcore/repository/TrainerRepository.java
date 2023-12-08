package com.epam.upskill.springcore.repository;

import com.epam.upskill.springcore.model.Trainer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    // Derived query method
    Optional<Trainer> findByUserUsername(String username);

    // Using @Query with pagination
    Page<Trainer> findByUserFirstNameContainingOrUserLastNameContaining(String firstName, String lastName, Pageable pageable);

    // Custom query for getting not assigned trainers
    @Query("SELECT t FROM Trainer t WHERE t.id NOT IN (SELECT t.id FROM Trainer t JOIN t.trainees tr WHERE tr.user.username = :username)")
    Page<Trainer> getNotAssignedTrainers(String username, Pageable pageable);

    Page<Trainer> findAllByTraineesUserUsernameNot(String username, Pageable pageable);

    List<Trainer> findAllByUserUsernameIn(Collection<String> user_username);
}