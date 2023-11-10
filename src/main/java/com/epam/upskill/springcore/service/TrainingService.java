package com.epam.upskill.springcore.service;

import com.epam.upskill.springcore.model.DTOs.TrainingDTO;
import com.epam.upskill.springcore.model.Training;
import com.epam.upskill.springcore.service.dbService.Specifications.TrainingSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @className: TrainingService  $
 * @description: TODO
 * @date: 08 November 2023 $
 * @time: 5:43 AM 30 $
 * @author: Qudratjon Komilov
 */


public interface TrainingService {
    TrainingDTO createOrUpdateTraining(Training trainer);

    TrainingDTO getTrainingById(Long id);

    List<TrainingDTO> getAllTrainings();

    Page<TrainingDTO> getTrainingsByFilter(Pageable pageable, TrainingSpecifications trainingSpecifications);
}

