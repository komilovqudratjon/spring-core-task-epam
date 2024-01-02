package com.epam.upskill.springcore.service.impl;

import com.epam.upskill.springcore.model.dtos.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityNotFoundException;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @description: TODO
 * @date: 09 December 2023 $
 * @time: 2:37 AM 08 $
 * @author: Qudratjon Komilov
 */
@SpringBootTest
class TraineeServiceImplTest {

    @Autowired
    private TraineeServiceImpl traineeService;

    @Autowired
    private UserServiceImpl userService;

    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Doe";
    private static final String USERNAME = "john.doe";


    @Order(3)
    @Test
    void update() {
        // Arrange

        Date dateOfBirth = new Date();
        String address = "London";
        ReqTraineeDTO dto = new ReqTraineeDTO(dateOfBirth, "user2",  LAST_NAME,FIRST_NAME,true, address);
        // Act
        TraineeDTO traineeDTO = traineeService.update(dto);
        // Assert
        assertNotNull(traineeDTO);
        assertEquals(FIRST_NAME, traineeDTO.user().firstName());
        assertEquals(LAST_NAME, traineeDTO.user().lastName());
        assertEquals(dateOfBirth, traineeDTO.user().dateOfBirth());
        assertEquals(address, traineeDTO.user().address());
    }

    @Test
    void getByUsername() {
        // Arrange
        String username = "user1";
        // Act
        TraineeDTO trainee = traineeService.getByUsername(username);
        // Assert
        assertNotNull(trainee);
        assertEquals(username, trainee.user().username());

    }

    @Test
    void deleteByUsername() {
        // Arrange
        String username = "user0";
        // Act
        traineeService.deleteByUsername(username);
        // Assert
        assertThrows(EntityNotFoundException.class, () -> {
            traineeService.getByUsername(username);
        });



    }

    @Test
    void addTrainers() {
        // Arrange
        String username = "user1";
        List<String> trainerUsernames = Arrays.asList("user10", "user11");
        // Act
        List<TrainerDTO> trainerDTOS = traineeService.addTrainers(username, trainerUsernames);
        // Assert
        assertNotNull(trainerDTOS);
        assertEquals(2, trainerDTOS.size());
        assertEquals(trainerUsernames.get(0), trainerDTOS.get(0).user().username());
        assertEquals(trainerUsernames.get(1), trainerDTOS.get(1).user().username());


    }

    @Order(1)
    @Test
    void register() {
        // Arrange
        ReqUserTraineeDTO traineeDTO = new ReqUserTraineeDTO(FIRST_NAME,LAST_NAME, new Date(), "London");
        // Act
        LoginResDTO register = traineeService.register(traineeDTO);
        // Assert
        assertNotNull(register);
        TraineeDTO byUsername = traineeService.getByUsername(USERNAME);
        // Assert
        assertNotNull(byUsername);
        assertEquals(USERNAME, byUsername.user().username());
        // Assert
        userService.login(register);

    }

    @Test
    void setActiveStatus() {
        // Arrange
        String username = "user1";
        boolean active = false;
        // Act
        traineeService.setActiveStatus(username, active);
        // Assert
        TraineeDTO byUsername = traineeService.getByUsername(username);
        assertNotNull(byUsername);
        assertEquals(active, byUsername.isActive());
    }
}