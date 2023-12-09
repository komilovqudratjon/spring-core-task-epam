package com.epam.upskill.springcore.service;

import com.epam.upskill.springcore.model.dtos.*;

import java.util.List;

/**
 * @description: Service interface for Trainer entity.
 * @date: 08 November 2023 $
 * @time: 5:43 AM 30 $
 * @author: Qudratjon Komilov
 */


public interface TrainerService {
    TrainerDTO update(ReqTrainerDTO trainerDTO);

    TrainerDTO getByUsername(String username);

    PageGeneral<TrainerDTO> getNotAssignedTrainers(String traineeId, Integer page, Integer size);

    LoginResDTO register(RestUserTrainerDTO trainerDTO);

    void activate(String username, boolean isActive);
}

