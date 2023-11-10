package com.epam.upskill.springcore.model.DTOs;

/**
 * @className: SpecializationRecord  $
 * @description: TODO
 * @date: 09 November 2023 $
 * @time: 3:00 PM 28 $
 * @author: Qudratjon Komilov
 */

import java.util.Date;

public record TrainingDTO(Long id, TrainerDTO trainer, TraineeDTO trainee, String trainingName,
                          TrainingTypeDTO trainingType, Date trainingDate, Integer trainingDuration) {
}


