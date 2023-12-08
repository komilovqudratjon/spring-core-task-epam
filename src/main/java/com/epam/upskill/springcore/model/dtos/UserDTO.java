package com.epam.upskill.springcore.model.dtos;

import java.util.Date;

/**
 * @className: SpecializationRecord  $
 * @description: TODO
 * @date: 09 November 2023 $
 * @time: 3:00 PM 28 $
 * @author: Qudratjon Komilov
 */
public record UserDTO(Long id,String address,  String firstName, String lastName, String username, Date dateOfBirth,  Boolean isActive) {

}


