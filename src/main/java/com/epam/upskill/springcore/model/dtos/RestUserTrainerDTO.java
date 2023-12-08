package com.epam.upskill.springcore.model.dtos;

import javax.validation.constraints.NotNull;

/**
 * @description: TODO
 * @date: 06 December 2023 $
 * @time: 4:55 AM 27 $
 * @author: Qudratjon Komilov
 */

public record RestUserTrainerDTO(
                                 @NotNull(message = "lastName should not be null") String lastName,
                                 @NotNull(message = "firstName should not be null")
                                 String firstName, Long specializationId) {
}
