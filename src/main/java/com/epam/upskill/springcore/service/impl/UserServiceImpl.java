package com.epam.upskill.springcore.service.impl;

import com.epam.upskill.springcore.model.Users;
import com.epam.upskill.springcore.model.dtos.LoginDTO;
import com.epam.upskill.springcore.model.dtos.LoginResDTO;
import com.epam.upskill.springcore.model.dtos.RestUserDTO;
import com.epam.upskill.springcore.model.dtos.UserDTO;
import com.epam.upskill.springcore.security.SecurityUtils;
import com.epam.upskill.springcore.service.UserService;
import com.epam.upskill.springcore.service.db.common.UserDatabase;
import com.epam.upskill.springcore.service.impl.mapper.UserDTOMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
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

    private final UserDatabase userDatabase;
    private final UserDTOMapper userDTOMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registers a new user in the system.
     * <p>
     * This method takes a user DTO, creates a new user entity, generates a unique username and a password,
     * and saves the user to the database. It returns login response data including the new username and password.
     *
     * @param userDTO The user data transfer object containing the user's information.
     * @return LoginResDTO containing the user's ID, username, and generated password.
     */
    @Override
    public LoginResDTO register(RestUserDTO userDTO) {
        Users user = new Users();
        user.setFirstName(userDTO.firstName());
        user.setLastName(userDTO.lastName());
        String username = generateUsername(userDTO.firstName(), userDTO.lastName());
        user.setUsername(username);
        String password = generatePassword();
        log.info("Generated password: {}", password);
        user.setPassword(passwordEncoder.encode(password));
        user.setIsActive(true);
        Users save = userDatabase.save(user);
        return new LoginResDTO(save.getId(), username, password);
    }

    /**
     * Handles user login.
     * <p>
     * This method verifies the username and password provided in the login response DTO.
     * If the credentials are incorrect, it throws a security exception.
     *
     * @param loginResDTO The login response DTO containing the username and password.
     * @throws EntityNotFoundException if the user is not found.
     * @throws SecurityException       if the password is incorrect.
     */
    @Override
    public void login(LoginResDTO loginResDTO) {
        Optional<Users> byUsername = userDatabase.findByUsername(loginResDTO.username());
        if (byUsername.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        } else {
            Users users = byUsername.get();
            if (!passwordEncoder.matches(loginResDTO.password(), users.getPassword())) {
                throw new SecurityException("Password is incorrect");
            }
        }
    }

    /**
     * Changes the password for the currently logged-in user.
     * <p>
     * This method first verifies if the current user is attempting to change their own password and then
     * checks if the old password is correct. If these checks pass, it updates the password.
     *
     * @param loginResDTO The DTO containing the old and new passwords.
     * @return LoginResDTO with updated password information.
     * @throws SecurityException       if the user tries to change another user's password or if the old password is incorrect.
     * @throws EntityNotFoundException if the user is not found.
     */
    @Override
    public LoginResDTO changePassword(LoginDTO loginResDTO) {
        String currentUserUsername = SecurityUtils.getCurrentUserUsername();
        if (!Objects.equals(currentUserUsername, loginResDTO.username())) {
            // throw forbidden exception
            throw new SecurityException("You are not allowed to change password for other users");
        }
        Optional<Users> byUsername = userDatabase.findByUsername(SecurityUtils.getCurrentUserUsername());
        if (byUsername.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        } else {
            if (!passwordEncoder.matches(loginResDTO.oldPassword(), byUsername.get().getPassword())) {
                throw new SecurityException("Old password is incorrect");
            }
            Users users = byUsername.get();
            users.setPassword(passwordEncoder.encode(loginResDTO.newPassword()));
            Users save = userDatabase.save(users);
            return new LoginResDTO(save.getId(), save.getUsername(), loginResDTO.newPassword());
        }
    }

    /**
     * Updates the user's information.
     * <p>
     * This method updates the first and last names of the currently logged-in user.
     *
     * @param restUserDTO The DTO containing the user's new first and last names.
     * @return UserDTO The updated user information.
     * @throws EntityNotFoundException if the user is not found.
     */
    @Override
    public UserDTO update(RestUserDTO restUserDTO) {
        Optional<Users> byUsername = userDatabase.findByUsername(SecurityUtils.getCurrentUserUsername());
        if (byUsername.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        } else {
            Users users = byUsername.get();
            users.setFirstName(restUserDTO.firstName());
            users.setLastName(restUserDTO.lastName());
            Users save = userDatabase.save(users);
            return userDTOMapper.apply(save);
        }
    }

    /**
     * Toggles the activation status of a user.
     * <p>
     * This method finds a user by their ID and toggles their active status.
     *
     * @param id The ID of the user whose active status needs to be toggled.
     * @throws EntityNotFoundException if the user is not found.
     */
    @Override
    public void activate(Long id) {
        Optional<Users> byId = userDatabase.findById(id);
        if (byId.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        } else {
            Users users = byId.get();
            users.setIsActive(!users.getIsActive());
            userDatabase.save(users);
        }
    }

    /**
     * Retrieves the user information for the currently logged-in user.
     * <p>
     * This method finds the currently logged-in user by their username and returns their information.
     *
     * @return UserDTO The DTO representation of the current user.
     * @throws EntityNotFoundException if the user is not found.
     */
    @Override
    public UserDTO getMe() {
        Optional<Users> byUsername = userDatabase.findByUsername(SecurityUtils.getCurrentUserUsername());
        if (byUsername.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        } else {
            Users users = byUsername.get();
            return userDTOMapper.apply(users);
        }
    }

    /**
     * Generates a unique username based on the user's first and last names.
     * <p>
     * This method concatenates the user's first and last names, checks for uniqueness in the database,
     * and appends a number if necessary to ensure uniqueness.
     *
     * @param firstName The user's first name.
     * @param lastName  The user's last name.
     * @return String A unique username.
     */
    public String generateUsername(String firstName, String lastName) {
        String baseUsername = firstName.toLowerCase() + "." + lastName.toLowerCase();
        String username = baseUsername;
        int counter = 1;

        while (userDatabase.existsByUsername(username)) {
            username = baseUsername + counter;
            counter++;
        }
        return username;
    }

    /**
     * Generates a password for a user.
     * <p>
     * This method should ideally generate a secure random password. Currently, it returns a fixed string.
     *
     * @return String A generated password.
     */
    public String generatePassword() {
        return RandomStringUtils.random(10, true, false);
    }
}