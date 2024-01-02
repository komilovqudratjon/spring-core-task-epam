package com.epam.upskill.springcore.service;

import com.epam.upskill.springcore.model.dtos.*;


import java.util.List;

/**
 * @description: Service interface for Trainee entity.
 * @date: 08 November 2023 $
 * @time: 5:42 AM 37 $
 * @author: Qudratjon Komilov
 */


public interface TraineeService {
    TraineeDTO update(ReqTraineeDTO trainee);

    TraineeDTO getByUsername(String username);

    void deleteByUsername(String username);

    List<TrainerDTO> addTrainers(String traineeUsername, List<String> trainerUsername);

    LoginResDTO register(ReqUserTraineeDTO restUserDTO);

    void setActiveStatus(String username, boolean isActive);
}

