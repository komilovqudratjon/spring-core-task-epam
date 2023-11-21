package com.epam.upskill.springcore.service;

import com.epam.upskill.springcore.model.dtos.LoginDTO;
import com.epam.upskill.springcore.model.dtos.RestToken;
import com.epam.upskill.springcore.model.dtos.RestUserDTO;
import com.epam.upskill.springcore.model.dtos.UserDTO;

/**
 * @description: Service interface for Training entity.
 * @date: 08 November 2023 $
 * @time: 5:43 AM 30 $
 * @author: Qudratjon Komilov
 */
public interface UserService {
    UserDTO register(RestUserDTO userDTO);

    RestToken login(LoginDTO loginDTO);

    UserDTO changePassword(LoginDTO loginDTO);

    UserDTO update(RestUserDTO restUserDTO);

    void activate(Long id);
}

