package com.epam.upskill.springcore.service.impl;

import com.epam.upskill.springcore.model.Trainer;
import com.epam.upskill.springcore.model.dtos.Page;
import com.epam.upskill.springcore.model.dtos.ResTrainerDTO;
import com.epam.upskill.springcore.model.dtos.TrainerDTO;
import com.epam.upskill.springcore.repository.SpecializationHibernate;
import com.epam.upskill.springcore.service.TrainerService;
import com.epam.upskill.springcore.service.db.common.TrainerDatabase;
import com.epam.upskill.springcore.service.impl.mapper.TrainerDTOMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
    private final SpecializationHibernate specializationHibernate;

    /**
     * Creates or updates a Trainer in the database.
     *
     * @param trainer the trainer to create or update
     * @return the created or updated TrainerDTO
     */
    @Override
    public TrainerDTO createOrUpdate(ResTrainerDTO trainer) {
        log.debug("Request to create/update trainer: {}", trainer);
        try {
            Trainer trainer1 = new Trainer();
            trainer1.setId(trainer.id());
            trainer1.setSpecialization(specializationHibernate.findById(trainer.specializationId()).orElseThrow(() -> {
                log.error("Specialization not found by id: {}", trainer.specializationId());
                return new EntityNotFoundException("Specialization not found by id: " + trainer.specializationId());
            }));
            trainer1.setUser(trainerRepository.findById(trainer.userId()).orElseThrow(() -> {
                log.error("User not found by id: {}", trainer.userId());
                return new EntityNotFoundException("User not found by id: " + trainer.userId());
            }).getUser());

            TrainerDTO apply = trainerDTOMapper.apply(trainerRepository.save(trainer1));
            log.info("Trainer created/updated successfully: {}", apply);
            return apply;
        } catch (Exception e) {
            log.error("Error creating/updating trainer: {}", e.getMessage());
            throw e; // or handle accordingly
        }
    }

    /**
     * Retrieves a Trainer by its ID.
     *
     * @param id the ID of the trainer
     * @return the TrainerDTO
     * @throws EntityNotFoundException if the trainer is not found
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
     * Retrieves all Trainers from the database.
     *
     * @return a list of TrainerDTOs
     */
    @Override
    public List<TrainerDTO> getAllTrainers() {
        log.debug("Request to retrieve all trainers");
        List<TrainerDTO> list = trainerRepository.findAll().stream().map(trainerDTOMapper).toList();
        log.info("All trainers retrieved successfully");
        return list;
    }

    /**
     * Retrieves a Trainer by its username.
     *
     * @param username the username of the trainer
     * @return the TrainerDTO
     * @throws EntityNotFoundException if the trainer is not found
     */
    @Override
    public TrainerDTO getByUsername(String username) {
        log.debug("Request to retrieve trainer by username: {}", username);
        return trainerDTOMapper.apply(trainerRepository.findByUserUsername(username).orElseThrow(() -> {
            log.error("Trainer not found for username: {}", username);
            return new EntityNotFoundException("Trainer not found for username: " + username);
        }));
    }

    @Override
    public Page<TrainerDTO> getByFilter(Integer page, Integer size, String search) {
        log.debug("Retrieving all trainees");
        Page<Trainer> trainees = trainerRepository.getByFilter(page, size, search);
        log.debug("Retrieved all trainees: {}", trainees);
        List<TrainerDTO> collect = trainees.getContent().stream().map(trainerDTOMapper).collect(Collectors.toList());
        return new Page<>(collect, trainees.getNumber(), trainees.getSize(), trainees.getTotalElements());
    }

    @Override
    public Page<TrainerDTO> getNotAssignedTrainers(Long traineeId) {
        log.debug("Retrieving all trainees");
        Page<Trainer> trainees = trainerRepository.getNotAssignedTrainers(traineeId);
        log.debug("Retrieved all trainees: {}", trainees);
        List<TrainerDTO> collect = trainees.getContent().stream().map(trainerDTOMapper).collect(Collectors.toList());
        return new Page<>(collect, trainees.getNumber(), trainees.getSize(), trainees.getTotalElements());
    }
}