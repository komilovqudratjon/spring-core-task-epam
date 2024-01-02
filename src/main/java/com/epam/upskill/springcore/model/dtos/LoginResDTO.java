package com.epam.upskill.springcore.model.dtos;

import io.swagger.annotations.ApiModelProperty;

/**
 * @className: SpecializationRecord  $
 * @description: TODO
 * @date: 09 November 2023 $
 * @time: 3:00 PM 28 $
 * @author: Qudratjon Komilov
 */
public record LoginResDTO(@ApiModelProperty(value = "User ID", example = "123", required = true)
                          Long id,
                          @ApiModelProperty(value = "Username", example = "john_doe", required = true)
                          String username,
                          @ApiModelProperty(value =  "user's password", example = "paswword123", required = true)
                          String password) {
}


