package com.epam.upskill.springcore.service;

import com.epam.upskill.springcore.model.DTOs.TraineeDTO;
import com.epam.upskill.springcore.model.Trainee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @className: TraineeService  $
 * @description: TODO
 * @date: 08 November 2023 $
 * @time: 5:42 AM 37 $
 * @author: Qudratjon Komilov
 */


public interface TraineeService {
    TraineeDTO createOrUpdateTrainee(Trainee trainee);

    void deleteTrainee(Long id);

    TraineeDTO getTraineeById(Long id);

    Page<TraineeDTO> getAllTrainees(Pageable pageable, Date dateOfBirth, String address);
}

