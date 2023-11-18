package com.epam.upskill.springcore.service.impl;

import com.epam.upskill.springcore.model.Trainee;
import com.epam.upskill.springcore.model.Users;
import com.epam.upskill.springcore.model.dtos.ResTraineeDTO;
import com.epam.upskill.springcore.model.dtos.TraineeDTO;
import com.epam.upskill.springcore.repository.UserHibernate;
import com.epam.upskill.springcore.service.db.common.TraineeDatabase;
import com.epam.upskill.springcore.service.impl.mapper.TraineeDTOMapper;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * @className: TraineeServiceImplTest  $
 * @description: Tests for TraineeServiceImpl
 * @date: 10 November 2023 $
 * @time: 3:24 AM 55 $
 * @author: Qudratjon Komilov
 */
class TraineeServiceImplTest {

    @Mock
    private TraineeDatabase traineeRepository;

    @Mock
    UserHibernate userHibernate;

    @Mock
    private TraineeDTOMapper traineeDTOMapper;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateOrUpdateTrainee() {
        // Arrange
        Lorem lorem = LoremIpsum.getInstance();

        Long userId = 3L;
        Users mockUser = new Users();
        mockUser.setId(userId);
        ResTraineeDTO traineeDTO = new ResTraineeDTO(1L, new Date(), lorem.getCity(), userId);
        when(userHibernate.findById(userId)).thenReturn(Optional.of(mockUser));
        when(traineeRepository.save(any(Trainee.class))).thenAnswer(i -> i.getArguments()[0]);
        when(userHibernate.findById(userId)).thenReturn(Optional.of(mockUser));
        when(traineeRepository.save(any(Trainee.class))).thenAnswer(i -> i.getArguments()[0]);

        // Act
        TraineeDTO result = traineeService.createOrUpdate(traineeDTO);

        // Assert
        assertNull(result);
        verify(traineeRepository).save(any(Trainee.class));
        verify(traineeDTOMapper).apply(any(Trainee.class));
    }


    @Test
    void testDeleteTrainee() {
        // Arrange
        Long id = 1L;

        // Act
        traineeService.delete(id);

        // Assert
        verify(traineeRepository).deleteById(id);
    }


    @Test
    void testGetTraineeById() {
        // Arrange
        Long id = 3L;
        Trainee mockTrainee = new Trainee();
        when(traineeRepository.findById(id)).thenReturn(Optional.of(mockTrainee));

        // Act&Assert
        assertThrows(EntityNotFoundException.class, () -> {
            traineeService.getById(id);
        });
        verify(traineeRepository).findById(id);
        verify(traineeDTOMapper).apply(mockTrainee);
    }


}
