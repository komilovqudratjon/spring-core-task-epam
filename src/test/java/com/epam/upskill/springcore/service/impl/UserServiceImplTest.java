package com.epam.upskill.springcore.service.impl;

import com.epam.upskill.springcore.service.UserService;
import com.epam.upskill.springcore.service.db.common.UserDatabase;
import com.epam.upskill.springcore.service.impl.mapper.UserDTOMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @description: TODO
 * @date: 09 December 2023 $
 * @time: 2:38 AM 00 $
 * @author: Qudratjon Komilov
 */
@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserDatabase userDatabase;

    @Mock
    private UserDTOMapper userDTOMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService = new UserServiceImpl(userDatabase, userDTOMapper, passwordEncoder);

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void register() {
        // Arrange
        // Create any necessary test data

        // Act
        // Call the method under test

        // Assert
        // Verify the results
    }

    @Test
    void login() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    void changePassword() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    void update() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    void activate() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    void getMe() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    void generateUsername() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    void generatePassword() {
        // Arrange

        // Act

        // Assert
    }
}