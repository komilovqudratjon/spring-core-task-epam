package com.epam.upskill.springcore.service;

import com.epam.upskill.springcore.model.DTOs.TrainerDTO;
import com.epam.upskill.springcore.model.Trainer;
import com.epam.upskill.springcore.service.dbService.common.TrainerDB;

import java.util.List;
import java.util.Optional;

/**
 * @className: TrainerService  $
 * @description: TODO
 * @date: 08 November 2023 $
 * @time: 5:43 AM 30 $
 * @author: Qudratjon Komilov
 */


public interface TrainerService {
    TrainerDTO createOrUpdateTrainer(Trainer trainer);

    TrainerDTO getTrainerById(Long id);

    List<TrainerDTO> getAllTrainers();
}

