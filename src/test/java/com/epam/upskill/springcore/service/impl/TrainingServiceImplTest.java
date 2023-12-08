package com.epam.upskill.springcore.service.impl;

import com.epam.upskill.springcore.model.*;
import com.epam.upskill.springcore.model.dtos.ResTrainingDTO;
import com.epam.upskill.springcore.model.dtos.TrainingDTO;
import com.epam.upskill.springcore.repository.TrainerRepository;
import com.epam.upskill.springcore.repository.TrainingTypeRepository;
import com.epam.upskill.springcore.service.db.common.TraineeDatabase;
import com.epam.upskill.springcore.service.db.common.TrainerDatabase;
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

import javax.persistence.EntityNotFoundException;
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
    private TrainerRepository trainerRepositoryMock; // Mock for the TrainerRepository


    @InjectMocks
    private TrainingServiceImpl trainingService;
    @Mock
    private TrainingDatabase trainingRepository;

    @Mock
    private TrainingDTOMapper trainingDTOMapper;

    @Mock
    private TrainerDatabase trainerRepository;

    @Mock
    private TraineeDatabase traineeRepository;

    @Mock
    private TrainingTypeRepository trainingTypeRepository;


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
        trainingDTO = trainingDTOMapper.apply(training);

    }

    @Test
    void createOrUpdateTraining_Success() {
        // Given
        ResTrainingDTO resTrainingDTO = new ResTrainingDTO(training.getId(), training.getTrainer().getId(), training.getTrainee().getId(), training.getTrainingName(), training.getTrainingType().getId(), training.getTrainingDate(), training.getTrainingDuration());
        when(trainerRepository.findById(any())).thenReturn(Optional.of(training.getTrainer()));
        when(traineeRepository.findById(any())).thenReturn(Optional.of(training.getTrainee()));
        when(trainingTypeRepository.findById(any())).thenReturn(Optional.of(training.getTrainingType()));
        when(trainingRepository.save(any())).thenReturn(training);
        when(trainingDTOMapper.apply(any())).thenReturn(trainingDTO);

        // When
        TrainingDTO result = trainingService.createOrUpdate(resTrainingDTO);

        // Then
        assertNull(result);
        assertEquals(trainingDTO, result);
        verify(trainingRepository).save(any(Training.class));
    }

    @Test
    void createOrUpdateTraining_TrainerNotFound() {
        // Given
        ResTrainingDTO resTrainingDTO = new ResTrainingDTO(training.getId(), training.getTrainer().getId(), training.getTrainee().getId(), training.getTrainingName(), training.getTrainingType().getId(), training.getTrainingDate(), training.getTrainingDuration());
        when(trainerRepository.findById(any())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> trainingService.createOrUpdate(resTrainingDTO));
    }

    // Similar tests for TraineeNotFound and TrainingTypeNotFound

    @Test
    void createOrUpdateTraining_Exception() {
        // Given
        ResTrainingDTO resTrainingDTO = new ResTrainingDTO(training.getId(), training.getTrainer().getId(), training.getTrainee().getId(), training.getTrainingName(), training.getTrainingType().getId(), training.getTrainingDate(), training.getTrainingDuration());
        when(trainerRepository.findById(any())).thenReturn(Optional.of(training.getTrainer()));
        when(traineeRepository.findById(any())).thenReturn(Optional.of(training.getTrainee()));
        when(trainingTypeRepository.findById(any())).thenReturn(Optional.of(training.getTrainingType()));
        when(trainingRepository.save(any())).thenThrow(new RuntimeException("Unexpected error"));

        // When & Then
        assertThrows(RuntimeException.class, () -> trainingService.createOrUpdate(resTrainingDTO));
    }

    @Test
    void getTrainingById() {
        when(trainingRepository.findById(anyLong())).thenReturn(Optional.of(training));
        when(trainingDTOMapper.apply(training)).thenReturn(trainingDTO);

        TrainingDTO result = trainingService.getById(1L);

        verify(trainingRepository).findById(1L);
        assertEquals(trainingDTO, result);
    }

    @Test
    void getTrainingById_NotFound() {
        when(trainingRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> trainingService.getById(1L));
    }

    @Test
    void getAllTrainings() {
        List<Training> trainingList = List.of(training);
        when(trainingRepository.findAll()).thenReturn(trainingList);
        when(trainingDTOMapper.apply(training)).thenReturn(trainingDTO);

        List<TrainingDTO> result = trainingService.getAll();

        verify(trainingRepository).findAll();
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(trainingDTO, result.get(0));
    }


}

