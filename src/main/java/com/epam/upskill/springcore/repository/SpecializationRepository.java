package com.epam.upskill.springcore.repository;

import com.epam.upskill.springcore.model.Specialization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @description: TODO
 * @date: 19 November 2023 $
 * @time: 2:48 AM 09 $
 * @author: Qudratjon Komilov
 */
@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long> {

}