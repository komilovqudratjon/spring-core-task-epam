package com.epam.upskill.springcore.service.impl.mapper;


import com.epam.upskill.springcore.model.dtos.UserDTO;
import com.epam.upskill.springcore.model.Users;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * @description: Mapper class for UserDTO.
 * @date: 09 November 2023 $
 * @time: 3:37 PM 25 $
 * @author: Qudratjon Komilov
 */
@Service
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
