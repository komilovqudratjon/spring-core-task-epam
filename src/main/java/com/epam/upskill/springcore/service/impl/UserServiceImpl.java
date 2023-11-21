package com.epam.upskill.springcore.service.impl;

import com.epam.upskill.springcore.model.Users;
import com.epam.upskill.springcore.model.dtos.LoginDTO;
import com.epam.upskill.springcore.model.dtos.RestToken;
import com.epam.upskill.springcore.model.dtos.RestUserDTO;
import com.epam.upskill.springcore.model.dtos.UserDTO;
import com.epam.upskill.springcore.repository.UserHibernate;
import com.epam.upskill.springcore.security.SecurityUtils;
import com.epam.upskill.springcore.service.UserService;
import com.epam.upskill.springcore.service.impl.mapper.UserDTOMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Objects;
import java.util.Optional;

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

    private final UserHibernate userHibernate;
    private final UserDTOMapper userDTOMapper;
    private final PasswordEncoder passwordEncoder;

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
        log.info("Generated password: {}", password);
        user.setPassword(passwordEncoder.encode(password));
        user.setIsActive(true);
        Users save = userHibernate.save(user);
        UserDTO apply = userDTOMapper.apply(save);
        return apply;
    }

    @Override
    public RestToken login(LoginDTO loginDTO) {
        return null;
    }

    @Override
    public UserDTO changePassword(LoginDTO loginDTO) {
        String currentUserUsername = SecurityUtils.getCurrentUserUsername();
        if (!Objects.equals(currentUserUsername, loginDTO.username())) {
            // throw forbidden exception
            throw new SecurityException("You are not allowed to change password for other users");
        }
        Optional<Users> byUsername = userHibernate.findByUsername(SecurityUtils.getCurrentUserUsername());
        if (byUsername.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        } else {
            Users users = byUsername.get();
            users.setPassword(passwordEncoder.encode(loginDTO.password()));
            Users save = userHibernate.save(users);
            return userDTOMapper.apply(save);
        }
    }

    @Override
    public UserDTO update(RestUserDTO restUserDTO) {
        Optional<Users> byUsername = userHibernate.findByUsername(SecurityUtils.getCurrentUserUsername());
        if (byUsername.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        } else {
            Users users = byUsername.get();
            users.setFirstName(restUserDTO.firstName());
            users.setLastName(restUserDTO.lastName());
            Users save = userHibernate.save(users);
            return userDTOMapper.apply(save);
        }
    }

    @Override
    public void activate(Long id) {
        Optional<Users> byId = userHibernate.findById(id);
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        } else {
            Users users = byId.get();
            users.setIsActive(!users.getIsActive());
            userHibernate.save(users);
        }
    }

    /**
     * Generates a unique username.
     *
     * @param firstName The first name of the user.
     * @param lastName  The last name of the user.
     * @return The generated username.
     */
    private String generateUsername(String firstName, String lastName) {
        String baseUsername = firstName.toLowerCase() + "." + lastName.toLowerCase();
        String username = baseUsername;
        int counter = 1;

        while (userHibernate.existsByUsername(username)) {
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
//        return RandomStringUtils.random(10, true, false);
        return "password";
    }
}