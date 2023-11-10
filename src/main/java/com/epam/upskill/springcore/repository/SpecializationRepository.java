package com.epam.upskill.springcore.repository;

import com.epam.upskill.springcore.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @className: SpecializationRepository  $
 * @description: TODO
 * @date: 08 November 2023 $
 * @time: 5:36 AM 25 $
 * @author: Qudratjon Komilov
 */

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long> {
    // Custom methods can be defined here if needed
}
