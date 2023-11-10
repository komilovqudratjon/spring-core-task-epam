package com.epam.upskill.springcore.service.impl.mapper;


import com.epam.upskill.springcore.model.DTOs.UserDTO;
import com.epam.upskill.springcore.model.Users;

import java.util.function.Function;

/**
 * @className: TraineeDTOMapper  $
 * @description: TODO
 * @date: 09 November 2023 $
 * @time: 3:37 PM 25 $
 * @author: Qudratjon Komilov
 */
public class UserDTOMapper implements Function<Users, UserDTO> {

    @Override
    public UserDTO apply(Users users) {
        return new UserDTO(
                users.getId(),
                users.getFirstName(),
                users.getLastName(),
                users.getUsername(),
                users.getIsActive()
        );
    }
}
