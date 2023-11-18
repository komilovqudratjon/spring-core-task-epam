package com.epam.upskill.springcore.service;

import com.epam.upskill.springcore.model.dtos.ResTrainingDTO;
import com.epam.upskill.springcore.model.dtos.TrainingDTO;

import java.util.List;

/**
 * @description: Service interface for Training entity.
 * @date: 08 November 2023 $
 * @time: 5:43 AM 30 $
 * @author: Qudratjon Komilov
 */


public interface TrainingService {
    TrainingDTO createOrUpdate(ResTrainingDTO trainer);

    TrainingDTO getById(Long id);

    List<TrainingDTO> getAll();
}

