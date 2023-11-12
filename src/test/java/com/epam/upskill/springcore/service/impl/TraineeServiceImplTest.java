package com.epam.upskill.springcore.service.impl;

import com.epam.upskill.springcore.model.dtos.TraineeDTO;
import com.epam.upskill.springcore.model.dtos.UserDTO;
import com.epam.upskill.springcore.model.Trainee;
import com.epam.upskill.springcore.model.Users;
import com.epam.upskill.springcore.service.db.specifications.TraineeSpecifications;
import com.epam.upskill.springcore.service.db.common.TraineeDatabase;
import com.epam.upskill.springcore.service.impl.mapper.TraineeDTOMapper;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private TraineeDTOMapper traineeDTOMapper;

    @InjectMocks
    private TraineeServiceImpl traineeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrUpdateTrainee() {
        Lorem lorem = LoremIpsum.getInstance();

        // Prepare test data
        Users user = new Users();
        user.setFirstName(lorem.getName());        // Random first name
        user.setLastName(lorem.getLastName());     // Random last name
        user.setUsername("user");              // Unique username based on index
        user.setPassword(lorem.getWords(1, 10));   // Random password
        user.setIsActive(true);
        Trainee trainee = new Trainee(1L, new Date(), "123 Main St", user);
        TraineeDTO expectedDto = new TraineeDTO(1L, trainee.getDateOfBirth(), trainee.getAddress(), new UserDTO(1L, user.getFirstName(), user.getLastName(), user.getUsername(), user.getIsActive()));

        // Define the behavior of mocks
        when(traineeRepository.save(any(Trainee.class))).thenReturn(trainee);
        when(traineeDTOMapper.apply(any(Trainee.class))).thenReturn(expectedDto);

        // Call the method to be tested
//        TraineeDTO actualDto = traineeService.createOrUpdateTrainee(trainee);

        // Verify the result

//        assertEquals(expectedDto, actualDto);

        // Verify the interactions
        verify(traineeRepository).save(trainee);
        verify(traineeDTOMapper).apply(trainee);
    }

    @Test
    void deleteTrainee() {
        // Arrange
        Long traineeId = 1L;

        // Act
        traineeService.deleteTrainee(traineeId);

        // Assert
        verify(traineeRepository).deleteById(traineeId);
    }

    @Test
    void getTraineeById() {
        Lorem lorem = LoremIpsum.getInstance();

        // Arrange
        Long traineeId = 1L;
        Trainee trainee = new Trainee(traineeId, new Date(), "123 Main St", new Users());
        TraineeDTO expectedDto = new TraineeDTO(traineeId, trainee.getDateOfBirth(), trainee.getAddress(), new UserDTO(1L, lorem.getFirstName(), lorem.getLastName(), lorem.getName(), true));

        when(traineeRepository.findById(traineeId)).thenReturn(Optional.of(trainee));
        when(traineeDTOMapper.apply(trainee)).thenReturn(expectedDto);

        // Act
        TraineeDTO actualDto = traineeService.getTraineeById(traineeId);

        // Assert
        assertEquals(expectedDto, actualDto);
        verify(traineeRepository).findById(traineeId);
        verify(traineeDTOMapper).apply(trainee);
    }

    @Test
    void getTraineesByFilter() {
        Lorem lorem = LoremIpsum.getInstance();

        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Date dateOfBirth = new Date();
        String address = "123 Main St";
        Users user = new Users();
        Trainee trainee = new Trainee(1L, dateOfBirth, address, user);
        TraineeDTO traineeDTO = new TraineeDTO(1L, dateOfBirth, address, new UserDTO(1L, lorem.getFirstName(), lorem.getLastName(), lorem.getName(), true));
        Page<Trainee> trainees = new PageImpl<>(Collections.singletonList(trainee));
        Page<TraineeDTO> expectedDtos = new PageImpl<>(Collections.singletonList(traineeDTO));

        when(traineeRepository.findAll(any(TraineeSpecifications.class), eq(pageable))).thenReturn(trainees);
        when(traineeDTOMapper.apply(any(Trainee.class))).thenReturn(traineeDTO);

        // Act
        Page<TraineeDTO> actualDtos = traineeService.getTraineesByFilter(pageable, dateOfBirth, address);

        // Assert
        assertEquals(expectedDtos.getTotalElements(), actualDtos.getTotalElements());
        assertEquals(expectedDtos.getContent().get(0), actualDtos.getContent().get(0));
        verify(traineeRepository).findAll(any(TraineeSpecifications.class), eq(pageable));
    }
}
