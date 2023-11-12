package com.epam.upskill.springcore.service.impl.mapper;


import com.epam.upskill.springcore.model.dtos.TrainingDTO;
import com.epam.upskill.springcore.model.Training;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * @description: Mapper class for TrainingDTO.
 * @date: 09 November 2023 $
 * @time: 3:37 PM 25 $
 * @author: Qudratjon Komilov
 */
@Service
@AllArgsConstructor
public class TrainingDTOMapper implements Function<Training, TrainingDTO> {
    private final TrainerDTOMapper trainerDTOMapper;
    private final TraineeDTOMapper traineeDTOMapper;
    private final TrainingTypeDTOMapper trainingTypeDTOMapper;

    @Override
    public TrainingDTO apply(Training training) {
        return new TrainingDTO(
                training.getId(),
                trainerDTOMapper.apply(training.getTrainer()),
                traineeDTOMapper.apply(training.getTrainee()),
                training.getTrainingName(),
                trainingTypeDTOMapper.apply(training.getTrainingType()),
                training.getTrainingDate(),
                training.getTrainingDuration()
        );
    }
}
