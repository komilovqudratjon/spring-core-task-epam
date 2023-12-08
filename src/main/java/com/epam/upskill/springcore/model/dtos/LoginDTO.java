package com.epam.upskill.springcore.model.dtos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @className: SpecializationRecord  $
 * @description: TODO
 * @date: 09 November 2023 $
 * @time: 3:00 PM 28 $
 * @author: Qudratjon Komilov
 */


@ApiModel(description = "Data Transfer Object for user login information")
public record LoginDTO(
        @ApiModelProperty(value = "User ID", example = "123", required = true)
        Long id,

        @ApiModelProperty(value = "Username", example = "john_doe", required = true)
        String username,

        @ApiModelProperty(value = "New password for the user", example = "newPassword123", required = true)
        String newPassword,

        @ApiModelProperty(value = "Old password of the user", example = "oldPassword123", required = true)
        String oldPassword
) {
}



