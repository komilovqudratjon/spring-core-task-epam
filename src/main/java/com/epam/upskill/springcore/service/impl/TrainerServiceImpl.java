package com.epam.upskill.springcore.service.impl;

import com.epam.upskill.springcore.model.RoleName;
import com.epam.upskill.springcore.model.Trainer;
import com.epam.upskill.springcore.model.Users;
import com.epam.upskill.springcore.model.dtos.*;
import com.epam.upskill.springcore.repository.SpecializationRepository;
import com.epam.upskill.springcore.service.TrainerService;
import com.epam.upskill.springcore.service.UserService;
import com.epam.upskill.springcore.service.db.common.TrainerDatabase;
import com.epam.upskill.springcore.service.db.common.UserDatabase;
import com.epam.upskill.springcore.service.impl.mapper.TrainerDTOMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: Service class for managing Trainer entities.
 * @date: 08 November 2023 $
 * @time: 5:43 AM 47 $
 * @author: Qudratjon Komilov
 */
@Service
@AllArgsConstructor
@Slf4j
public class TrainerServiceImpl implements TrainerService {

    private final TrainerDatabase trainerRepository;
    private final TrainerDTOMapper trainerDTOMapper;
    private final SpecializationRepository specializationRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserDatabase userDatabase;


    /**
     * Updates a trainer's details based on the provided TrainerDTO.
     * If the trainer or the associated specialization doesn't exist, it throws an EntityNotFoundException.
     *
     * @param trainerDTO Data Transfer Object containing trainer's information.
     * @return The updated TrainerDTO.
     * @throws EntityNotFoundException If the trainer or specialization is not found.
     */
    @Override
    public TrainerDTO update(ReqTrainerDTO trainerDTO) {
        log.debug("Request to create/update trainerDTO: {}", trainerDTO);

        Trainer trainer = trainerRepository.findByUserUsername(trainerDTO.username()).orElseThrow(() -> {
            log.error("Trainer not found for username: {}", trainerDTO.username());
            return new EntityNotFoundException("Trainer not found for username: " + trainerDTO.username());
        });

        trainer.setSpecialization(specializationRepository.findById(trainerDTO.specializationId()).orElseThrow(() -> {
            log.error("Specialization not found by id: {}", trainerDTO.specializationId());
            return new EntityNotFoundException("Specialization not found by id: " + trainerDTO.specializationId());
        }));
        Users user = trainerRepository.findByUserUsername(trainerDTO.username()).orElseThrow(() -> {
            log.error("User not found by id: {}", trainerDTO.username());
            return new EntityNotFoundException("User not found by id: " + trainerDTO.username());
        }).getUser();
        user.setFirstName(trainerDTO.firstName());
        user.setLastName(trainerDTO.lastName());
        user.setDateOfBirth(trainerDTO.birthDate());
        user.setAddress(trainerDTO.address());
        userDatabase.save(user);
        trainer.setUser(user);

        TrainerDTO apply = trainerDTOMapper.apply(trainerRepository.save(trainer));
        log.info("Trainer created/updated successfully: {}", apply);
        return apply;

    }

    /**
     * Retrieves a trainer by their unique identifier.
     * Throws an EntityNotFoundException if the trainer is not found.
     *
     * @param id The unique identifier of the trainer.
     * @return TrainerDTO corresponding to the given id.
     * @throws EntityNotFoundException If no trainer is found for the given id.
     */
    @Override
    public TrainerDTO getById(Long id) {
        log.debug("Request to retrieve trainer by id: {}", id);
        return trainerDTOMapper.apply(trainerRepository.findById(id).orElseThrow(() -> {
            log.error("Trainer not found for id: {}", id);
            return new EntityNotFoundException("Trainer not found for id: " + id);
        }));
    }

    /**
     * Retrieves all trainers from the database.
     *
     * @return A list of TrainerDTOs for all trainers.
     */
    @Override
    public List<TrainerDTO> getAllTrainers() {
        log.debug("Request to retrieve all trainers");
        List<TrainerDTO> list = trainerRepository.findAll().stream().map(trainerDTOMapper).toList();
        log.info("All trainers retrieved successfully");
        return list;
    }

    /**
     * Retrieves a trainer by their username.
     * Throws an EntityNotFoundException if the trainer is not found.
     *
     * @param username The username of the trainer.
     * @return TrainerDTO corresponding to the given username.
     * @throws EntityNotFoundException If no trainer is found with the given username.
     */
    @Override
    public TrainerDTO getByUsername(String username) {
        log.debug("Request to retrieve trainer by username: {}", username);
        return trainerDTOMapper.apply(trainerRepository.findByUserUsername(username).orElseThrow(() -> {
            log.error("Trainer not found for username: {}", username);
            return new EntityNotFoundException("Trainer not found for username: " + username);
        }));
    }

    /**
     * Retrieves a page of trainers based on the given filter criteria.
     *
     * @param page   The page number of the result set.
     * @param size   The size of the page.
     * @param search The search criteria to filter trainers.
     * @return A PageGeneral object containing a list of TrainerDTOs and pagination information.
     */
    @Override
    public PageGeneral<TrainerDTO> getByFilter(Integer page, Integer size, String search) {
        log.debug("Retrieving all trainees");
        Page<Trainer> trainees = trainerRepository.getByFilter(page, size, search);
        log.debug("Retrieved all trainees: {}", trainees);
        List<TrainerDTO> collect = trainees.getContent().stream().map(trainerDTOMapper).collect(Collectors.toList());
        return new PageGeneral<>(collect, trainees.getNumber(), trainees.getSize(), trainees.getTotalElements());
    }

    /**
     * Retrieves a page of trainers who are not assigned, based on the given criteria.
     *
     * @param username The username to exclude from the results.
     * @param page     The page number of the result set.
     * @param size     The size of the page.
     * @return A PageGeneral object containing a list of TrainerDTOs and pagination information.
     */
    @Override
    public PageGeneral<TrainerDTO> getNotAssignedTrainers(String username, Integer page, Integer size) {
        log.debug("Retrieving all trainees");
        Page<Trainer> trainees = trainerRepository.getNotAssignedTrainers(username, page, size);
        log.debug("Retrieved all trainees: {}", trainees);
        List<TrainerDTO> collect = trainees.getContent().stream().map(trainerDTOMapper).collect(Collectors.toList());
        return new PageGeneral<>(collect, trainees.getNumber(), trainees.getSize(), trainees.getTotalElements());
    }

    /**
     * Registers a new trainer based on the provided RestUserTrainerDTO.
     * It also generates a username and password for the trainer.
     *
     * @param trainerDTO Data Transfer Object containing the new trainer's information.
     * @return LoginResDTO containing the generated username and password.
     * @throws EntityNotFoundException If the specialization is not found.
     */
    @Transactional
    @Override
    public LoginResDTO register(RestUserTrainerDTO trainerDTO) {
        log.debug("Request to register trainee: {}", trainerDTO);
        Users user = new Users();
        user.setFirstName(trainerDTO.firstName());
        user.setLastName(trainerDTO.lastName());
        String username = userService.generateUsername(trainerDTO.firstName(), trainerDTO.lastName());
        user.setUsername(username);
        user.setRole(RoleName.ROLE_TRAINER);

        String password = userService.generatePassword();
        log.info("Generated password: {}", password);
        user.setPassword(passwordEncoder.encode(password));
        user.setIsActive(true);
        user = userDatabase.save(user);
        log.debug("Request to create/update trainerDTO: {}", trainerDTO);
        Trainer trainer = new Trainer();
        trainer.setSpecialization(specializationRepository.findById(trainerDTO.specializationId()).orElseThrow(() -> {
            log.error("Specialization not found by id: {}", trainerDTO.specializationId());
            return new EntityNotFoundException("Specialization not found by id: " + trainerDTO.specializationId());
        }));
        trainer.setUser(user);
        TrainerDTO apply = trainerDTOMapper.apply(trainerRepository.save(trainer));
        log.info("Trainer created/updated successfully: {}", apply);
        return new LoginResDTO(user.getId(), username, password);
    }

    /**
     * Activates or deactivates a trainer based on the given username.
     *
     * @param username The username of the trainer to be activated/deactivated.
     * @param isActive The activation status to be set.
     */
    @Override
    public void activate(String username, boolean isActive) {
        log.debug("Request to activate trainer: {}", username);
        trainerRepository.activate(username, isActive);
        log.info("Trainer activated successfully: {}", username);
    }

}