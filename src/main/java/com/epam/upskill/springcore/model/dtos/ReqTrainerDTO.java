package com.epam.upskill.springcore.model.dtos;

import io.swagger.annotations.ApiModelProperty;

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
public record ReqTrainerDTO(
        @NotNull(message = "username should not be null")
        @ApiModelProperty(value = "Unique username of the trainer", example = "john_doe")
        String username,

        @NotNull(message = "lastName should not be null")
        @ApiModelProperty(value = "Last name of the trainer", example = "Doe")
        String lastName,

        @NotNull(message = "firstName should not be null")
        @ApiModelProperty(value = "First name of the trainer", example = "John")
        String firstName,

        @Pattern(regexp = "^[a-zA-Z0-9\\s,.'-]{2,30}$", message = "Address should be between 2 and 30 characters long and can include alphanumeric characters and ,.'-")
        @ApiModelProperty(value = "Address of the trainer", example = "123 Main St")
        String address,

        @ApiModelProperty(value = "Birthdate of the trainer", example = "1980-01-01")
        Date birthDate,

        @NotNull(message = "active should not be null")
        @ApiModelProperty(value = "Active status of the trainer", example = "true")
        Boolean active,

        @NotNull(message = "Specialization ID should not be null")
        @ApiModelProperty(value = "ID of the trainer's specialization", example = "1")
        Long specializationId) {
}


