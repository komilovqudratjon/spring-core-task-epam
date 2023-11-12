package com.epam.upskill.springcore.model.dtos;

/**
 * @className: SpecializationRecord  $
 * @description: TODO
 * @date: 09 November 2023 $
 * @time: 3:00 PM 28 $
 * @author: Qudratjon Komilov
 */

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

public record ResTrainingDTO(Long id,
                             @NotNull(message = "Trainer ID should not be null")
                             Long trainerId,
                             @NotNull(message = "Trainee ID should not be null")
                             Long traineeId,
                             @NotNull(message = "Training name should not be null")
                             @NotEmpty(message = "Training name should not be empty")
                             @Pattern(regexp = "^[a-zA-Z0-9]{2,30}$",
                                     message = "Training name should be between 2 and 30 characters long")
                             String trainingName,
                             @NotNull(message = "Training type ID should not be null")
                             Long trainingTypeId,
                             Date trainingDate,
                             @NotNull(message = "Training duration should not be null")
                             Integer trainingDuration) {
}


