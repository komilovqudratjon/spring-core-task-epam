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

    void delete(Long id);

    TraineeDTO getById(Long id);

    PageGeneral<TraineeDTO> getByFilter(int page, int size, String name);

    TraineeDTO getByUsername(String username);

    void deleteByUsername(String username);

    List<TrainerDTO> addTrainers(String traineeUsername, List<String> trainerUsername);

    LoginResDTO register(ReqUserTraineeDTO restUserDTO);

    void activate(String username, boolean isActive);
}

