package com.epam.upskill.springcore.service.impl;

import com.epam.upskill.springcore.model.Specialization;
import com.epam.upskill.springcore.model.Trainer;
import com.epam.upskill.springcore.model.Users;
import com.epam.upskill.springcore.model.dtos.ResTrainerDTO;
import com.epam.upskill.springcore.model.dtos.SpecializationDTO;
import com.epam.upskill.springcore.model.dtos.TrainerDTO;
import com.epam.upskill.springcore.model.dtos.UserDTO;
import com.epam.upskill.springcore.repository.SpecializationRepository;
import com.epam.upskill.springcore.service.db.common.TrainerDatabase;
import com.epam.upskill.springcore.service.impl.mapper.TrainerDTOMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @className: TrainerServiceImplTest  $
 * @description: TODO
 * @date: 10 November 2023 $
 * @time: 4:13 AM 17 $
 * @author: Qudratjon Komilov
 */


class TrainerServiceImplTest {

    @Mock
    private TrainerDatabase trainerRepository;

    @Mock
    private TrainerDTOMapper trainerDTOMapper;

    @Mock
    private SpecializationRepository specializationRepository;

    @InjectMocks
    private TrainerServiceImpl trainerService;

    private Trainer trainer;
    private TrainerDTO trainerDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Create static test data for Users and Specialization
        Users user = new Users(1L, "John", "Doe", "johndoe", "password123", true);
        Specialization specialization = new Specialization(1L, "Java Developer");

        // Now, create a new Trainer with the User and Specialization
        trainer = new Trainer(1L, specialization, user);

        // Prepare the TrainerDTO based on the Trainer data
        trainerDTO = new TrainerDTO(trainer.getId(), new SpecializationDTO(specialization.getId(), specialization.getSpecializationName()), new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getFirstName(), user.getIsActive()));
    }

    @Test
    void createOrUpdateTrainer_Success() {
        // Given
        ResTrainerDTO resTrainerDTO = new ResTrainerDTO(trainer.getId(), trainer.getSpecialization().getId(), trainer.getUser().getId());
        when(specializationRepository.findById(any())).thenReturn(Optional.of(trainer.getSpecialization()));
        when(trainerRepository.findById(any())).thenReturn(Optional.of(trainer));
        when(trainerRepository.save(any())).thenReturn(trainer);
        when(trainerDTOMapper.apply(any())).thenReturn(trainerDTO);

        // When
        TrainerDTO result = trainerService.createOrUpdate(resTrainerDTO);

        // Then
        assertNotNull(result);
        assertEquals(trainerDTO, result);
        verify(trainerRepository).save(any(Trainer.class));
    }

    @Test
    void createOrUpdateTrainer_SpecializationNotFound() {
        // Given
        ResTrainerDTO resTrainerDTO = new ResTrainerDTO(trainer.getId(), trainer.getSpecialization().getId(), trainer.getUser().getId());
        when(specializationRepository.findById(any())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> trainerService.createOrUpdate(resTrainerDTO));
    }

    @Test
    void createOrUpdateTrainer_UserNotFound() {
        // Given
        ResTrainerDTO resTrainerDTO = new ResTrainerDTO(trainer.getId(), trainer.getSpecialization().getId(), trainer.getUser().getId());
        when(specializationRepository.findById(any())).thenReturn(Optional.of(trainer.getSpecialization()));
        when(trainerRepository.findById(any())).thenReturn(Optional.empty());

        // When & Then
        assertThrows(EntityNotFoundException.class, () -> trainerService.createOrUpdate(resTrainerDTO));
    }

    @Test
    void createOrUpdateTrainer_Exception() {
        // Given
        ResTrainerDTO resTrainerDTO = new ResTrainerDTO(trainer.getId(), trainer.getSpecialization().getId(), trainer.getUser().getId());
        when(specializationRepository.findById(any())).thenReturn(Optional.of(trainer.getSpecialization()));
        when(trainerRepository.findById(any())).thenReturn(Optional.of(trainer));
        when(trainerRepository.save(any())).thenThrow(new RuntimeException("Unexpected error"));

        // When & Then
        assertThrows(RuntimeException.class, () -> trainerService.createOrUpdate(resTrainerDTO));
    }
    @Test
    void getTrainerById_found() {
        when(trainerRepository.findById(anyLong())).thenReturn(Optional.of(trainer));
        when(trainerDTOMapper.apply(any(Trainer.class))).thenReturn(trainerDTO);

        TrainerDTO result = trainerService.getTrainerById(1L);

        assertNotNull(result);
        assertEquals(trainerDTO, result);

        verify(trainerRepository).findById(1L);
        verify(trainerDTOMapper).apply(trainer);
    }

    @Test
    void getTrainerById_notFound() {
        when(trainerRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> trainerService.getTrainerById(1L));

        verify(trainerRepository).findById(1L);
        verify(trainerDTOMapper, never()).apply(any(Trainer.class));
    }

    @Test
    void getAllTrainers() {
        when(trainerRepository.findAll()).thenReturn(List.of(trainer));
        when(trainerDTOMapper.apply(any(Trainer.class))).thenReturn(trainerDTO);

        List<TrainerDTO> result = trainerService.getAllTrainers();

        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(trainerDTO, result.get(0)); // assuming that trainerDTO is the expected result

        verify(trainerRepository).findAll();
        verify(trainerDTOMapper).apply(trainer);
    }
}
