package com.epam.upskill.springcore.service.impl;

import com.epam.upskill.springcore.model.Training;
import com.epam.upskill.springcore.model.TrainingType;
import com.epam.upskill.springcore.model.dtos.PageGeneral;
import com.epam.upskill.springcore.model.dtos.ResTrainingDTO;
import com.epam.upskill.springcore.model.dtos.TrainingDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @description: TODO
 * @date: 09 December 2023 $
 * @time: 2:37 AM 39 $
 * @author: Qudratjon Komilov
 */
@SpringBootTest
class TrainingServiceImplTest {
    @Autowired
    private TrainingServiceImpl trainingService;

    @Autowired
    private GenerateDateTest generateDateTest;



    @Test
    void createOrUpdate() {
        // Arrange
        ResTrainingDTO training = new ResTrainingDTO(null, "user10", "user1", "Swimming", 2L, new Date(), 60);
        // Act
        TrainingDTO result = trainingService.createOrUpdate(training);
        // Assert
        assertNotNull(result);
        assertNotNull(result.id());

    }


    @Test
    void getTraineeTrainings() {
        // Arrange
        String username = "user0";
        Date periodFrom = new Date(new Date().getTime()- 86400000L *30*2); // Adjust to appropriate test date
        Date periodTo = new Date(new Date().getTime()+ 86400000L *30); // Adjust to appropriate test date
        String trainerName = null;
        String trainingType = null;
        int page = 0;
        int size = 10;



        // Act
        PageGeneral<TrainingDTO> result = trainingService.getTraineeTrainings(username, periodFrom, periodTo, trainerName, trainingType, page, size);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void getTrainerTrainings() {
        // Arrange
        String username = "user10";
        Date periodFrom = new Date(new Date().getTime()- 86400000L *30*2); // Adjust to appropriate test date
        Date periodTo = new Date(new Date().getTime()+ 86400000L *30); // Adjust to appropriate test date
        String trainerName = null;
        String trainingType = null;
        int page = 0;
        int size = 10;



        // Act
        PageGeneral<TrainingDTO> result = trainingService.getTrainerTrainings(username, periodFrom, periodTo, trainerName, trainingType, page, size);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
    }

    @Test
    void getTrainingTypes() {
        // Arrange
        // Act
        List<TrainingType> result = trainingService.getTrainingTypes();

        // Assert
        assertNotNull(result);
        // greter then 3 because we have 3 training types in data.sql
        assertFalse(result.isEmpty());
    }
}