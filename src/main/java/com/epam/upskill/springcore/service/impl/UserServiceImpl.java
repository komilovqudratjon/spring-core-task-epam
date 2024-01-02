package com.epam.upskill.springcore.service.impl;

import com.epam.upskill.springcore.model.UserSession;
import com.epam.upskill.springcore.model.Users;
import com.epam.upskill.springcore.model.dtos.*;
import com.epam.upskill.springcore.security.JwtUtil;
import com.epam.upskill.springcore.service.UserService;
import com.epam.upskill.springcore.service.db.common.UserDatabase;
import com.epam.upskill.springcore.service.impl.mapper.UserDTOMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

/**
 * @description: Service class for managing Training entities.
 * @date: 08 November 2023 $
 * @time: 5:43 AM 47 $
 * @author: Qudratjon Komilov
 */

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserDatabase userDatabase;
    private final UserDTOMapper userDTOMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticate;
    private final JwtUtil jwtUtil;

    @Value("${app.userBlockTimeInMs}")
    private Integer timeBlocked;

    @Value("${app.userBlockCount}")
    private long jwtExpirationInMs;

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
    public JwtResponse login(LoginResDTO loginResDTO) {
        Optional<Users> byUsername = userDatabase.findByUsername(loginResDTO.username());
        if (byUsername.isEmpty()) {
            throw new EntityNotFoundException("Invalid username/password supplied");
        } else {
            Users users = byUsername.get();
            if (users.getBlockedEndDate() != null && users.getBlockedEndDate().after(new Date())) {
                users.setCount(users.getCount() + 1);
                users.setBlockedEndDate(new Date(users.getBlockedEndDate().getTime() + timeBlocked));
                userDatabase.save(users);
                throw new SecurityException("User is blocked, try again few minutes later");
            }
            try {
                Authentication authentication = authenticate.authenticate(new UsernamePasswordAuthenticationToken(users.getUsername(), loginResDTO.password()));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                users.setCount(0);
                users.setBlockedEndDate(null);
                userDatabase.save(users);
            } catch (Exception e) {
                users.setCount(users.getCount() + 1);
                if (users.getCount() >= 3) {
                    users.setBlockedEndDate(new Date(System.currentTimeMillis() + timeBlocked));
                }
                userDatabase.save(users);
                throw new SecurityException("Invalid username/password supplied");
            }

            UserSession newUserSession = jwtUtil.getNewUserSession(users);
            return JwtResponse.builder()
                    .accessToken(newUserSession.getAccessToken())
                    .refreshToken(newUserSession.getRefreshToken())
                    .sessionId(newUserSession.getSessionId())
                    .username(users.getUsername())
                    .lastName(users.getLastName())
                    .firstName(users.getFirstName())
                    .role(users.getRole())
                    .build();
        }
    }

    /**
     * Changes the password for the currently logged-in user.
     * <p>
     * This method first verifies if the current user is attempting to change their own password and then
     * checks if the old password is correct. If these checks pass, it updates the password.
     *
     * @param loginResDTO The DTO containing the old and new passwords.
     * @param user        The currently logged-in user.
     * @return LoginResDTO with updated password information.
     * @throws SecurityException       if the user tries to change another user's password or if the old password is incorrect.
     * @throws EntityNotFoundException if the user is not found.
     */
    @Override
    public LoginResDTO changePassword(LoginDTO loginResDTO, Users user) {
        if (!Objects.equals(user.getUsername(), loginResDTO.username())) {
            // throw forbidden exception
            throw new SecurityException("You are not allowed to change password for other users");
        }
        Optional<Users> byUsername = userDatabase.findByUsername(user.getUsername());
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
    public UserDTO getMe(Users user) {
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        } else {
            return userDTOMapper.apply(user);
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

    @Override
    public JwtResponse refreshToken(JwtToken refreshToken) {


        UserSession updateUserSession = jwtUtil.getUpdateUserSession(refreshToken.getToken());

        return JwtResponse.builder()
                .accessToken(updateUserSession.getAccessToken())
                .refreshToken(updateUserSession.getRefreshToken())
                .sessionId(updateUserSession.getSessionId())
                .username(updateUserSession.getUser().getUsername())
                .lastName(updateUserSession.getUser().getLastName())
                .firstName(updateUserSession.getUser().getFirstName())
                .role(updateUserSession.getUser().getRole())
                .build();
    }

    @Override
    public void logout(String authToken, Users user) {
        boolean b = jwtUtil.deleteSession(authToken, user);
        if (!b) {
            throw new RuntimeException("User session not found");
        }
    }
}