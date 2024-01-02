package com.epam.upskill.springcore.service;

import com.epam.upskill.springcore.model.Users;
import com.epam.upskill.springcore.model.dtos.*;

/**
 * @description: Service interface for Training entity.
 * @date: 08 November 2023 $
 * @time: 5:43 AM 30 $
 * @author: Qudratjon Komilov
 */
public interface UserService {
    LoginResDTO register(RestUserDTO userDTO);

    JwtResponse login(LoginResDTO loginResDTO);

    LoginResDTO changePassword(LoginDTO loginResDTO, Users user);

    void activate(Long id);

    UserDTO getMe(Users user);

    String generateUsername(String firstName, String lastName);
    String generatePassword();

    JwtResponse refreshToken(JwtToken refreshToken);

    void logout(String authToken, Users user);
}

