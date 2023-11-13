package com.epam.upskill.springcore.service.impl;

import com.epam.upskill.springcore.model.Users;
import com.epam.upskill.springcore.model.dtos.RestUserDTO;
import com.epam.upskill.springcore.model.dtos.UserDTO;
import com.epam.upskill.springcore.repository.UserRepository;
import com.epam.upskill.springcore.service.UserService;
import com.epam.upskill.springcore.service.impl.mapper.UserDTOMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

/**
 * @description: Service class for managing Training entities.
 * @date: 08 November 2023 $
 * @time: 5:43 AM 47 $
 * @author: Qudratjon Komilov
 */

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;


    /**
     * Creates or updates a training.
     *
     * @param userDTO The training data transfer object.
     * @return ResponseEntity containing the created or updated TrainingDTO.
     */
    @Override
    public UserDTO register(RestUserDTO userDTO) {
        Users user = new Users();
        user.setFirstName(userDTO.firstName());
        user.setLastName(userDTO.lastName());
        String username = generateUsername(userDTO.firstName(), userDTO.lastName());
        user.setUsername(username);
        String password = generatePassword();
        user.setPassword(password);
        user.setIsActive(true);
        Users save = userRepository.save(user);
        return userDTOMapper.apply(save);
    }

    /**
     * Generates a unique username.
     *
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     * @return The generated username.
     */
    private String generateUsername(String firstName, String lastName) {
        String baseUsername = firstName + "." + lastName;
        String username = baseUsername;
        int counter = 1;

        while (userRepository.existsByUsername(username)) {
            username = baseUsername + counter;
            counter++;
        }
        return username;
    }

    /**
     * Generates a random password.
     *
     * @return The generated password.
     */
    private String generatePassword() {
        return RandomStringUtils.random(10, false, false);
    }
}