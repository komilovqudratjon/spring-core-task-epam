package com.epam.upskill.springcore.repository;

import com.epam.upskill.springcore.model.TrainingType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @description: Repository interface for TrainingType entity.
 * @date: 08 November 2023 $
 * @time: 5:36 AM 48 $
 * @author: Qudratjon Komilov
 */

public interface TrainingTypeRepository extends JpaRepository<TrainingType, Long> {
    
}
