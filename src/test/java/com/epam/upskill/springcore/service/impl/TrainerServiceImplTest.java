package com.epam.upskill.springcore.service.impl;

import com.epam.upskill.springcore.model.dtos.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @description: TODO
 * @date: 09 December 2023 $
 * @time: 2:37 AM 24 $
 * @author: Qudratjon Komilov
 */
@SpringBootTest
class TrainerServiceImplTest {
    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String USERNAME = "john.doe";
    @Autowired
    private TrainerServiceImpl trainerService;

    @Autowired
    private UserServiceImpl userService;

    @Test
    void update() {
        // Arrange
        String username = "user10";
        String firstName = "John";
        String lastName = "Doe";
        Long specialization = 1L;
        String address = "123 Main St";
        Date birthDate = new Date();
        boolean active = true;
        // Act
        ReqTrainerDTO trainerDTO = new ReqTrainerDTO(username, lastName,firstName, address, birthDate, active, specialization);
        TrainerDTO update = trainerService.update(trainerDTO);
        // Assert
        assertNotNull(update);
        assertEquals(username, update.user().username());
        assertEquals(firstName, update.user().firstName());
        assertEquals(lastName, update.user().lastName());
        assertEquals(address, update.user().address());
        assertEquals(birthDate, update.user().dateOfBirth());
        assertEquals(specialization, update.specialization().id());
    }

    @Test
    void getByUsername() {
        // Arrange
        String username = "user10";
        // Act
        TrainerDTO byUsername = trainerService.getByUsername(username);
        // Assert
        assertNotNull(byUsername);
        assertEquals(username, byUsername.user().username());
    }

    @Test
    void getNotAssignedTrainers() {
        // Arrange
        String username = "user10";
        int page = 0;
        int size = 10;
        // Act
        PageGeneral<TrainerDTO> notAssignedTrainers = trainerService.getNotAssignedTrainers(username, page, size);
        // Assert
        assertNotNull(notAssignedTrainers);
        assertEquals(page, notAssignedTrainers.getNumber());
    }

    @Test
    void register() {
        // Arrange
        RestUserTrainerDTO trainerDTO = new RestUserTrainerDTO(LAST_NAME, FIRST_NAME, 1L);
        // Act
        LoginResDTO register = trainerService.register(trainerDTO);
        // Assert
        userService.login(register);
        assertNotNull(register);
        TrainerDTO byUsername = trainerService.getByUsername(register.username());
        // Assert
        assertNotNull(byUsername);

    }

    @Test
    void activate() {
        // Arrange
        String username = "user10";
        boolean active = true;
        // Act
        trainerService.activate(username, active);
        // Assert
        TrainerDTO byUsername = trainerService.getByUsername(username);
        assertNotNull(byUsername);
        assertEquals(byUsername.user().isActive(), active);
    }
}