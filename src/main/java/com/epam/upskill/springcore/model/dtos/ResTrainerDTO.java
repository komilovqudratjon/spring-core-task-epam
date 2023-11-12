package com.epam.upskill.springcore.model.dtos;

import javax.validation.constraints.NotNull;

/**
 * @className: SpecializationRecord  $
 * @description: TODO
 * @date: 09 November 2023 $
 * @time: 3:00 PM 28 $
 * @author: Qudratjon Komilov
 */
public record ResTrainerDTO(Long id,
                            @NotNull(message = "Specialization ID should not be null")
                            Long specializationId,
                            @NotNull(message = "User ID should not be null")
                            Long userId) {
}


