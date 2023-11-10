package com.epam.upskill.springcore.repository;

import com.epam.upskill.springcore.model.Trainee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @className: TraineeRepository  $
 * @description: TODO
 * @date: 08 November 2023 $
 * @time: 5:37 AM 43 $
 * @author: Qudratjon Komilov
 */


@Repository
public interface TraineeRepository extends JpaRepository<Trainee, Long> {
    Page<Trainee> findAll(Specification<Trainee> spec, Pageable pageable);
}

