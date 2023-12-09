package com.epam.upskill.springcore.service.impl;

import com.epam.upskill.springcore.model.dtos.LoginDTO;
import com.epam.upskill.springcore.model.dtos.LoginResDTO;
import com.epam.upskill.springcore.model.dtos.RestUserDTO;
import com.epam.upskill.springcore.model.dtos.UserDTO;
import com.epam.upskill.springcore.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @description: TODO
 * @date: 09 December 2023 $
 * @time: 2:38 AM 00 $
 * @author: Qudratjon Komilov
 */
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String USERNAME = "john.doe";

    private static String PASSWORD = null;

    @Test
    void register() {
        // Arrange
        RestUserDTO userDTO = new RestUserDTO(null, FIRST_NAME, LAST_NAME);

        // Act
        LoginResDTO result = userService.register(userDTO);


        // Assert
        assertNotNull(result);
        assertNotNull(result.id());
        assertEquals("john.doe", result.username());
        PASSWORD = result.password();
    }

    @Test
    void login() {
        // Arrange
        LoginResDTO loginResDTO = new LoginResDTO(null, USERNAME, PASSWORD);


        // Act
        userService.login(loginResDTO);

        // Assert
    }

    @Test
    void changePassword() {
        Authentication auth = new UsernamePasswordAuthenticationToken(USERNAME, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        // Arrange
        LoginDTO loginDTO = new LoginDTO(null, USERNAME, PASSWORD + PASSWORD, PASSWORD);

        // Act
        LoginResDTO loginResDTO = userService.changePassword(loginDTO);

        // Assert
        userService.login(loginResDTO);

    }


    @Test
    void activate() {
        // Arrange
        Long id = 1L;
        // Act
        userService.activate(id);

        // Assert
    }

    @Test
    void getMe() {
        // Arrange
        Authentication auth = new UsernamePasswordAuthenticationToken(USERNAME, null, List.of(new SimpleGrantedAuthority("ROLE_USER")));
        SecurityContextHolder.getContext().setAuthentication(auth);
        // Act
        UserDTO me = userService.getMe();

        // Assert
        assertNotNull(me);
        assertEquals(USERNAME, me.username());
    }

}