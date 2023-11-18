package com.epam.upskill.springcore.service;

import com.epam.upskill.springcore.model.dtos.Page;
import com.epam.upskill.springcore.model.dtos.ResTraineeDTO;
import com.epam.upskill.springcore.model.dtos.TraineeDTO;


import java.util.Date;

/**
 * @description: Service interface for Trainee entity.
 * @date: 08 November 2023 $
 * @time: 5:42 AM 37 $
 * @author: Qudratjon Komilov
 */


public interface TraineeService {
    TraineeDTO createOrUpdate(ResTraineeDTO trainee);

    void delete(Long id);

    TraineeDTO getById(Long id);

    Page<TraineeDTO> getByFilter(int page, int size, String name);
}

