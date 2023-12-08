package com.epam.upskill.springcore.model.dtos;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * @description: TODO
 * @date: 05 December 2023 $
 * @time: 6:25 PM 23 $
 * @author: Qudratjon Komilov
 */
public record ReqUserTraineeDTO(
        @NotNull(message = "firstName should not be null")
        @ApiModelProperty(value = "First name of the trainee", required = true, example = "John")
        String firstName,

        @NotNull(message = "lastName should not be null")
        @ApiModelProperty(value = "Last name of the trainee", required = true, example = "Doe")
        String lastName,

        @ApiModelProperty(value = "Date of birth of the trainee", example = "1990-01-01")
        Date dateOfBirth,

        @Pattern(regexp = "^[a-zA-Z0-9\\s,.'-]{2,30}$", message = "Address should be between 2 and 30 characters long and can include alphanumeric characters and ,.'-")
        @ApiModelProperty(value = "Address of the trainee", example = "123 Main St")
        String address) {
}
