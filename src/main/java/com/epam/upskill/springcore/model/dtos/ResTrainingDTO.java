package com.epam.upskill.springcore.model.dtos;

/**
 * @className: SpecializationRecord  $
 * @description: TODO
 * @date: 09 November 2023 $
 * @time: 3:00 PM 28 $
 * @author: Qudratjon Komilov
 */

import io.swagger.annotations.ApiParam;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

public record ResTrainingDTO(@ApiParam(value = "Unique identifier of the training session", required = true)
                             Long id,

                             @ApiParam(value = "Username of the trainer", required = true)
                             @NotNull(message = "Trainer Username should not be null")
                             String trainerUsername,

                             @ApiParam(value = "Username of the trainee", required = true)
                             @NotNull(message = "Trainee Username should not be null")
                             String traineeUsername,

                             @ApiParam(value = "Name of the training session", required = true, allowableValues = "range[2, 30]")
                             @NotNull(message = "Training name should not be null")
                             @NotEmpty(message = "Training name should not be empty")
                             @Pattern(regexp = "^[a-zA-Z0-9\\s,.'-]{2,30}$", message = "Training name should be between 2 and 30 characters long and can include alphanumeric characters and ,.'-")
                             String trainingName,

                             @ApiParam(value = "Identifier for the training type", required = true)
                             @NotNull(message = "Training type ID should not be null")
                             Long trainingTypeId,

                             @ApiParam(value = "Date and time when the training is scheduled")
                             Date trainingDate,

                             @ApiParam(value = "Duration of the training session in minutes", required = true)
                             @NotNull(message = "Training duration should not be null")
                             Integer trainingDuration) {
}


