package com.epam.upskill.springcore.model.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @className: SpecializationRecord  $
 * @description: TODO
 * @date: 09 November 2023 $
 * @time: 3:00 PM 28 $
 * @author: Qudratjon Komilov
 */
public record ResSpecializationDTO(Long id,
                                   @NotNull(message = "Specialization name should not be null")
                                   @Pattern(regexp = "^[a-zA-Z0-9]{2,30}$",
                                           message = "Specialization name should be between 2 and 30 characters long")
                                   String specializationName) {
}

