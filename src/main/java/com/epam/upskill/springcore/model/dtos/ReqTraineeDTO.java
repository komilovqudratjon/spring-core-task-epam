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
public record ReqTraineeDTO(@ApiModelProperty(value = "Date of birth of the trainee", example = "1990-01-01")
                            Date dateOfBirth,

                            @NotNull(message = "username should not be null")
                            @ApiModelProperty(value = "Username of the trainee", required = true, example = "johndoe")
                            String username,

                            @NotNull(message = "lastName should not be null")
                            @ApiModelProperty(value = "Last name of the trainee", required = true, example = "Doe")
                            String lastName,

                            @NotNull(message = "firstName should not be null")
                            @ApiModelProperty(value = "First name of the trainee", required = true, example = "John")
                            String firstName,

                            @NotNull(message = "active should not be null")
                            @ApiModelProperty(value = "Active status of the trainee", required = true, example = "true")
                            Boolean active,

                            @Pattern(regexp = "^[a-zA-Z0-9\\s,.'-]{2,30}$", message = "Address should be between 2 and 30 characters long and can include alphanumeric characters and ,.'-")
                            @ApiModelProperty(value = "Address of the trainee", example = "123 Main St")
                            String address) {
}


