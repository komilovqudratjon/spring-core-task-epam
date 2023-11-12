package com.epam.upskill.springcore.service.impl;

import com.epam.upskill.springcore.model.dtos.TrainingDTO;
import com.epam.upskill.springcore.model.*;
import com.epam.upskill.springcore.service.db.specifications.TrainingSpecifications;
import com.epam.upskill.springcore.service.db.common.TrainingDatabase;
import com.epam.upskill.springcore.service.impl.mapper.TrainingDTOMapper;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * @className: TrainingServiceImplTest  $
 * @description: TODO
 * @date: 10 November 2023 $
 * @time: 4:54 AM 30 $
 * @author: Qudratjon Komilov
 */


@ExtendWith(MockitoExtension.class)
class TrainingServiceImplTest {

    @Mock
    private TrainingDatabase trainingDBMock;

    @Mock
    private TrainingDTOMapper trainingDTOMapperMock;

    @InjectMocks
    private TrainingServiceImpl trainingService;

    // Sample entities for use in tests
    private Training training;
    private TrainingDTO trainingDTO;

    @BeforeEach
    void setUp() {
        Lorem lorem = LoremIpsum.getInstance();
        Users user = new Users();
        user.setFirstName(lorem.getName());        // Random first name
        user.setLastName(lorem.getLastName());     // Random last name
        user.setUsername("user");              // Unique username based on index
        user.setPassword(lorem.getWords(1, 10));   // Random password
        user.setIsActive(true);
        // Initialize your training and trainingDTO objects here with dummy data
        Trainee trainee = Trainee.builder()
                .dateOfBirth(new Date()) // Replace with a randomly generated Date
                .address("Random address")
                .user(user) // Associate the User we just created
                .build();
        Specialization specialization = Specialization.builder()
                .specializationName(lorem.getWords(1, 3)) // Random specializationId name
                .build();
        Trainer trainer = Trainer.builder()
                .user(user) // Associate the User we just created
                .specialization(specialization) // Associate the Specialization we just created
                .build();

        TrainingType trainingType = TrainingType.builder()
                .trainingTypeName(lorem.getWords(1, 3)) // Random training type name
                .build();
        training = Training.builder()
                .trainee(trainee) // Associate the Trainee we just created
                .trainer(trainer) // Associate the Trainer we just created
                .trainingName("Random training name")
                .trainingType(trainingType) // Associate the TrainingType we just created
                .trainingDate(new Date()) // Replace with a randomly generated Date
                .trainingDuration(1) // Random training duration
                .build();
        trainingDTO = trainingDTOMapperMock.apply(training);
    }

    @Test
    void createOrUpdateTraining() {
        when(trainingDBMock.save(any(Training.class))).thenReturn(training);
        when(trainingDTOMapperMock.apply(training)).thenReturn(trainingDTO);

//        TrainingDTO result = trainingService.createOrUpdateTraining(training);

        verify(trainingDBMock).save(training);
//        assertEquals(trainingDTO, result);
    }

    @Test
    void getTrainingById() {
        when(trainingDBMock.findById(anyLong())).thenReturn(Optional.of(training));
        when(trainingDTOMapperMock.apply(training)).thenReturn(trainingDTO);

        TrainingDTO result = trainingService.getTrainingById(1L);

        verify(trainingDBMock).findById(1L);
        assertEquals(trainingDTO, result);
    }

    @Test
    void getTrainingById_NotFound() {
        when(trainingDBMock.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> trainingService.getTrainingById(1L));
    }

    @Test
    void getAllTrainings() {
        List<Training> trainingList = List.of(training);
        when(trainingDBMock.findAll()).thenReturn(trainingList);
        when(trainingDTOMapperMock.apply(training)).thenReturn(trainingDTO);

        List<TrainingDTO> result = trainingService.getAllTrainings();

        verify(trainingDBMock).findAll();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(trainingDTO, result.get(0));
    }


    @Test
    void getTrainingsByFilter() {
        // Create a mock Pageable and TrainingSpecifications
        Pageable pageableMock = mock(Pageable.class);
        TrainingSpecifications trainingSpecificationsMock = mock(TrainingSpecifications.class);

        // Create a mock Page<Training> to be returned by the repository
        Page<Training> trainingPageMock = mock(Page.class);
        when(trainingDBMock.findAll(trainingSpecificationsMock, pageableMock)).thenReturn(trainingPageMock);

        // Create a mock Page<TrainingDTO> which we will assert
        Page<TrainingDTO> trainingDTOPageMock = mock(Page.class);
        when(trainingPageMock.map(trainingDTOMapperMock)).thenReturn(trainingDTOPageMock);

        // Call the method to test
        Page<TrainingDTO> result = trainingService.getTrainingsByFilter(pageableMock, trainingSpecificationsMock);

        // Verify the interactions
        verify(trainingDBMock).findAll(trainingSpecificationsMock, pageableMock);
        verify(trainingPageMock).map(trainingDTOMapperMock);

        // Assert the result
        assertEquals(trainingDTOPageMock, result);
    }

}

