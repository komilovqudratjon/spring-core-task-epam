package com.epam.upskill.springcore.repository;

import com.epam.upskill.springcore.model.Trainee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @description: Repository interface for Trainee entity.
 * @date: 08 November 2023 $
 * @time: 5:37 AM 43 $
 * @author: Qudratjon Komilov
 */
public interface TraineeRepository extends JpaRepository<Trainee, Long> {

    Optional<Trainee> findByUserUsername(String username);

    @Transactional
    @Modifying
    @Query(value = "DELETE  FROM trainee WHERE user_id=:id", nativeQuery = true)
    void deleteId(Long id);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM trainer_trainee WHERE trainee_id in (SELECT id FROM trainee WHERE user_id=:id)", nativeQuery = true)
    void deleteByTrainerTraineeById(Long id);


    Page<Trainee> findByUserFirstNameContainingOrUserLastNameContaining(String firstName, String lastName, Pageable pageable);

}

