package com.epam.upskill.springcore.service.impl.mapper;


import com.epam.upskill.springcore.model.DTOs.TraineeDTO;
import com.epam.upskill.springcore.model.DTOs.TrainingDTO;
import com.epam.upskill.springcore.model.Trainee;
import com.epam.upskill.springcore.model.Training;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * @className: TraineeDTOMapper  $
 * @description: TODO
 * @date: 09 November 2023 $
 * @time: 3:37 PM 25 $
 * @author: Qudratjon Komilov
 */
@Service
public class TrainingDTOMapper implements Function<Training, TrainingDTO> {
    @Override
    public TrainingDTO apply(Training training) {
        return new TrainingDTO(
                training.getId(),
                new TrainerDTOMapper().apply(training.getTrainer()),
                new TraineeDTOMapper().apply(training.getTrainee()),
                training.getTrainingName(),
                new TrainingTypeDTOMapper().apply(training.getTrainingType()),
                training.getTrainingDate(),
                training.getTrainingDuration()
        );
    }
}
