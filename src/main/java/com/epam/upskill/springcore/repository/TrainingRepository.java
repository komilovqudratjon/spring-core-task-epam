package com.epam.upskill.springcore.repository;

import com.epam.upskill.springcore.model.Training;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @description: Repository interface for Training entity.
 * @date: 08 November 2023 $
 * @time: 5:38 AM 25 $
 * @author: Qudratjon Komilov
 */

public interface TrainingRepository extends JpaRepository<Training, Long> {

    void deleteByTraineeId(Long trainerId);

    Page<Training> findAll(Specification<Training> spec, Pageable pageable);

    List<Training> findAllByTraineeId(Long id);
}

