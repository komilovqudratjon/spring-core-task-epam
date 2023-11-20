package com.epam.upskill.springcore.service;

import com.epam.upskill.springcore.model.dtos.Page;
import com.epam.upskill.springcore.model.dtos.ResTrainerDTO;
import com.epam.upskill.springcore.model.dtos.TraineeDTO;
import com.epam.upskill.springcore.model.dtos.TrainerDTO;

import java.util.List;

/**
 * @description: Service interface for Trainer entity.
 * @date: 08 November 2023 $
 * @time: 5:43 AM 30 $
 * @author: Qudratjon Komilov
 */


public interface TrainerService {
    TrainerDTO createOrUpdate(ResTrainerDTO trainer);

    TrainerDTO getById(Long id);

    List<TrainerDTO> getAllTrainers();

    TrainerDTO getByUsername(String username);

    Page<TrainerDTO> getByFilter(Integer page, Integer size, String search);

    Page<TrainerDTO> getNotAssignedTrainers(Long traineeId);
}

