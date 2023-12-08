package com.epam.upskill.springcore.service.impl;

import com.epam.upskill.springcore.model.RoleName;
import com.epam.upskill.springcore.model.Trainee;
import com.epam.upskill.springcore.model.Users;
import com.epam.upskill.springcore.model.dtos.*;
import com.epam.upskill.springcore.service.TraineeService;
import com.epam.upskill.springcore.service.UserService;
import com.epam.upskill.springcore.service.db.common.TraineeDatabase;
import com.epam.upskill.springcore.service.db.common.UserDatabase;
import com.epam.upskill.springcore.service.impl.mapper.TraineeDTOMapper;
import com.epam.upskill.springcore.service.impl.mapper.TrainerDTOMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @description: Service class for managing Trainee entities.
 * @date: 08 November 2023 $
 * @time: 5:43 AM 02 $
 * @author: Qudratjon Komilov
 */
@Service
@AllArgsConstructor
@Slf4j
public class TraineeServiceImpl implements TraineeService {

    private final TraineeDatabase traineeRepository;

    private final UserDatabase userDatabase;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final TraineeDTOMapper traineeDTOMapper;
    private final TrainerDTOMapper trainerDTOMapper;

    /**
     * Updates the details of an existing trainee or creates a new one based on the provided TraineeDTO.
     * It throws an EntityNotFoundException if the trainee is not found by username.
     *
     * @param traineeDTO Data Transfer Object containing trainee details.
     * @return Updated TraineeDTO.
     */
    @Transactional
    @Override
    public TraineeDTO update(ReqTraineeDTO traineeDTO) {
        log.debug("Creating or updating trainee: {}", traineeDTO);
        Trainee trainee = traineeRepository.findByUsername(traineeDTO.username()).orElseThrow(() -> {
            log.error("User not found by username: {}", traineeDTO.username());
            return new EntityNotFoundException("User not found by username: " + traineeDTO.username());
        });
        Users user = trainee.getUser();
        user.setDateOfBirth(traineeDTO.dateOfBirth());
        user.setAddress(traineeDTO.address());
        user.setFirstName(traineeDTO.firstName());
        user.setLastName(traineeDTO.lastName());
        userDatabase.save(user);
        trainee.setUser(user);
        TraineeDTO apply = traineeDTOMapper.apply(traineeRepository.save(trainee));
        log.debug("Created or updated trainee: {}", apply);
        return apply;
    }


    /**
     * Deletes a trainee by their unique identifier.
     *
     * @param id The unique identifier of the trainee to be deleted.
     */
    @Override
    public void delete(Long id) {
        log.debug("Deleting trainee by id: {}", id);
        traineeRepository.deleteById(id);
        log.debug("Deleted trainee by id: {}", id);
    }

    /**
     * Retrieves a trainee by their unique identifier.
     * It throws an EntityNotFoundException if the trainee is not found.
     *
     * @param id The unique identifier of the trainee.
     * @return TraineeDTO of the requested trainee.
     */
    @Override
    public TraineeDTO getById(Long id) {
        log.debug("Retrieving trainee by id: {}", id);
        Optional<Trainee> byId = traineeRepository.findById(id);
        log.debug("Retrieved trainee by id: {}", byId);
        return byId.map(traineeDTOMapper).orElseThrow(() -> {
            log.error("Trainee not found by id: {}", id);
            return new EntityNotFoundException("Trainee not found by id: " + id);
        });
    }

    /**
     * Retrieves a page of trainees filtered by the given criteria.
     *
     * @param page   The page number to retrieve.
     * @param size   The number of records per page.
     * @param search The search criteria to apply.
     * @return PageGeneral containing a list of TraineeDTOs and page information.
     */
    @Override
    public PageGeneral<TraineeDTO> getByFilter(int page, int size, String search) {
        log.debug("Retrieving all trainees");
        Page<Trainee> trainees = traineeRepository.getByFilter(page, size, search);
        log.debug("Retrieved all trainees: {}", trainees);
        List<TraineeDTO> collect = trainees.getContent().stream().map(traineeDTOMapper).collect(Collectors.toList());
        return new PageGeneral<>(collect, trainees.getNumber(), trainees.getSize(), trainees.getTotalElements());

    }

    /**
     * Retrieves a trainee by their username.
     * It throws an EntityNotFoundException if the trainee is not found by username.
     *
     * @param username The username of the trainee.
     * @return TraineeDTO of the requested trainee.
     */
    @Override
    public TraineeDTO getByUsername(String username) {
        log.debug("Retrieving trainee by username: {}", username);
        Optional<Trainee> byUsername = traineeRepository.findByUsername(username);
        log.debug("Retrieved trainee by username: {}", byUsername);
        return byUsername.map(traineeDTOMapper).orElseThrow(() -> {
            log.error("Trainee not found by username: {}", username);
            return new EntityNotFoundException("Trainee not found by username: " + username);
        });
    }

    /**
     * Deletes a trainee by their username.
     *
     * @param username The username of the trainee to be deleted.
     */
    @Override
    public void deleteByUsername(String username) {
        log.debug("Deleting trainee by username: {}", username);
        traineeRepository.deleteByUsername(username);
        log.debug("Deleted trainee by username: {}", username);
    }

    /**
     * Adds trainers to a trainee identified by their username.
     *
     * @param traineeUsername The username of the trainee.
     * @param trainerUsername A list of usernames of trainers to be added.
     * @return List of TrainerDTOs added to the trainee.
     */
    @Override
    public List<TrainerDTO> addTrainers(String traineeUsername, List<String> trainerUsername) {
        return traineeRepository.addTrainers(traineeUsername, trainerUsername).stream().map(trainerDTOMapper).collect(Collectors.toList());
    }

    /**
     * Registers a new trainee with the details provided in ReqUserTraineeDTO.
     * Generates a username and password for the trainee.
     *
     * @param traineeDTO Data Transfer Object containing new trainee details.
     * @return LoginResDTO containing the trainee's ID, username, and generated password.
     */
    @Transactional
    @Override
    public LoginResDTO register(ReqUserTraineeDTO traineeDTO) {
        log.debug("Creating or updating trainee: {}", traineeDTO);
        Users user = new Users();
        user.setFirstName(traineeDTO.firstName());
        user.setLastName(traineeDTO.lastName());
        user.setDateOfBirth(traineeDTO.dateOfBirth());
        user.setRole(RoleName.ROLE_TRAINEE);
        String username = userService.generateUsername(traineeDTO.firstName(), traineeDTO.lastName());
        user.setUsername(username);
        String password = userService.generatePassword();
        log.info("Generated password: {}", password);
        user.setPassword(passwordEncoder.encode(password));
        user.setIsActive(true);
        user.setAddress(traineeDTO.address());
        user = userDatabase.save(user);
        log.debug("Request to create/update traineeDTO: {}", traineeDTO);
        Trainee trainer = new Trainee();
        trainer.setIsActive(true);
        trainer.setUser(user);
        TraineeDTO apply = traineeDTOMapper.apply(traineeRepository.save(trainer));
        log.info("Trainee created/updated successfully: {}", apply);
        return new LoginResDTO(user.getId(), username, password);
    }

    /**
     * Activates or deactivates a trainee identified by their username.
     *
     * @param username The username of the trainee.
     * @param isActive The status to set for the trainee's active state.
     */
    @Override
    public void activate(String username, boolean isActive) {
        log.debug("Activating trainee by username: {}", username);
        traineeRepository.activate(username, isActive);
        log.debug("Activated trainee by username: {}", username);
    }
}

