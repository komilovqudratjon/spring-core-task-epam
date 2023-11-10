package com.epam.upskill.springcore.repository;

import com.epam.upskill.springcore.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @className: TrainerRepository  $
 * @description: TODO
 * @date: 08 November 2023 $
 * @time: 5:38 AM 07 $
 * @author: Qudratjon Komilov
 */


@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    // Custom methods can be defined here if needed
}

