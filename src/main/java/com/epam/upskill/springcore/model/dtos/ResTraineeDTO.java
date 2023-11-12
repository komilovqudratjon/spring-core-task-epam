package com.epam.upskill.springcore.model.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @className: SpecializationRecord  $
 * @description: TODO
 * @date: 09 November 2023 $
 * @time: 3:00 PM 28 $
 * @author: Qudratjon Komilov
 */
public record ResTraineeDTO(Long id,
                            Date dateOfBirth,

                            @Pattern(regexp = "^[a-zA-Z0-9]{2,30}$",
                                    message = "address should be between 2 and 30 characters long")
                            String address,
                            @NotNull(message = "User ID should not be null")
                            Long userId) {
}


