package com.epam.upskill.springcore.service.impl;

import com.epam.upskill.springcore.model.Training;
import com.epam.upskill.springcore.model.TrainingType;
import com.epam.upskill.springcore.model.dtos.PageGeneral;
import com.epam.upskill.springcore.model.dtos.ResTrainingDTO;
import com.epam.upskill.springcore.model.dtos.TrainingDTO;
import com.epam.upskill.springcore.repository.TrainingTypeRepository;
import com.epam.upskill.springcore.service.TrainingService;
import com.epam.upskill.springcore.service.db.common.TraineeDatabase;
import com.epam.upskill.springcore.service.db.common.TrainerDatabase;
import com.epam.upskill.springcore.service.db.common.TrainingDatabase;
import com.epam.upskill.springcore.service.impl.mapper.TrainingDTOMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @description: Service class for managing Training entities.
 * @date: 08 November 2023 $
 * @time: 5:43 AM 47 $
 * @author: Qudratjon Komilov
 */

@Service
@AllArgsConstructor
@Slf4j
public class TrainingServiceImpl implements TrainingService {

    private final TrainingDatabase trainingRepository;
    private final TrainingDTOMapper trainingDTOMapper;
    private final TrainerDatabase trainerRepository;
    private final TraineeDatabase traineeRepository;
    private final TrainingTypeRepository trainingTypeRepository;

    /**
     * Creates or updates a training record based on the provided ResTrainingDTO.
     * If a training with the given ID exists, it updates the record; otherwise, it creates a new one.
     * It also handles the association of trainer, trainee, and training type based on their identifiers.
     *
     * @param training The training data transfer object containing the information for the training.
     * @return The created or updated TrainingDTO.
     * @throws EntityNotFoundException if the trainer, trainee, or training type is not found.
     */
    @Override
    public TrainingDTO createOrUpdate(ResTrainingDTO training) {
        log.debug("Request to create or update training: {}", training);
        Training trainer = new Training();
        trainer.setId(training.id());
        trainer.setTrainer(trainerRepository.findByUserUsername(training.trainerUsername()).orElseThrow(() -> {
            log.error("Trainer not found by id: {}", training.trainerUsername());
            return new EntityNotFoundException("Trainer not found by id: " + training.trainerUsername());
        }));
        trainer.setTrainee(traineeRepository.findByUsername(training.traineeUsername()).orElseThrow(() -> {
            log.error("Trainee not found by id: {}", training.traineeUsername());
            return new EntityNotFoundException("Trainee not found by id: " + training.traineeUsername());
        }));
        trainer.setTrainingName(training.trainingName());
        trainer.setTrainingType(trainingTypeRepository.findById(training.trainingTypeId()).orElseThrow(() -> {
            log.error("Training type not found by id: {}", training.trainingTypeId());
            return new EntityNotFoundException("Training type not found by id: " + training.trainingTypeId());
        }));
        trainer.setTrainingDate(training.trainingDate());
        trainer.setTrainingDuration(training.trainingDuration());

        TrainingDTO apply = trainingDTOMapper.apply(trainingRepository.save(trainer));
        log.info("Trainer created or updated successfully: {}", apply);
        return apply;
    }

    /**
     * Retrieves a training record by its ID.
     *
     * @param id The ID of the training to be retrieved.
     * @return The TrainingDTO corresponding to the given ID.
     * @throws EntityNotFoundException if the training with the specified ID is not found.
     */
    @Override
    public TrainingDTO getById(Long id) {
        log.debug("Request to retrieve training by id: {}", id);
        Optional<Training> training = trainingRepository.findById(id);

        return trainingDTOMapper.apply(training.orElseThrow(() -> {
            log.error("Trainer not found by id: {}", id);
            return new EntityNotFoundException("Trainer not found by id: " + id);
        }));
    }

    /**
     * Retrieves all training records.
     *
     * @return A list of TrainingDTOs representing all trainings.
     */
    @Override
    public List<TrainingDTO> getAll() {
        log.debug("Request to retrieve all trainings");
        List<TrainingDTO> list = trainingRepository.findAll().stream().map(trainingDTOMapper).toList();
        log.info("All trainings retrieved successfully");
        return list;
    }

    /**
     * Retrieves a paginated list of trainings for a specific trainee within a given period and optional filters.
     *
     * @param username     The username of the trainee.
     * @param periodFrom   The start date of the period.
     * @param periodTo     The end date of the period.
     * @param trainerName  Optional filter by trainer's name.
     * @param trainingType Optional filter by training type.
     * @param page         The page number for pagination.
     * @param size         The size of each page.
     * @return A paginated representation of TrainingDTOs for a trainee.
     */
    @Override
    public PageGeneral<TrainingDTO> getTraineeTrainings(String username, Date periodFrom, Date periodTo, String trainerName, String trainingType, int page, int size) {
        log.debug("Retrieving all trainees");
        Page<Training> traineeTrainings = trainingRepository.getTraineeTrainings(username, periodFrom, periodTo, trainerName, trainingType, page, size);
        log.debug("Retrieved all trainees: {}", traineeTrainings);
        return new PageGeneral<>(traineeTrainings.getContent().stream().map(trainingDTOMapper).toList(), traineeTrainings.getNumber(), traineeTrainings.getSize(), traineeTrainings.getTotalElements());

    }

    /**
     * Retrieves a paginated list of trainings conducted by a specific trainer within a given period and optional filters.
     *
     * @param username     The username of the trainer.
     * @param periodFrom   The start date of the period.
     * @param periodTo     The end date of the period.
     * @param traineeName  Optional filter by trainee's name.
     * @param trainingType Optional filter by training type.
     * @param page         The page number for pagination.
     * @param size         The size of each page.
     * @return A paginated representation of TrainingDTOs for a trainer.
     */
    @Override
    public PageGeneral<TrainingDTO> getTrainerTrainings(String username, Date periodFrom, Date periodTo, String traineeName, String trainingType, int page, int size) {
        log.debug("Retrieving all trainees");
        Page<Training> trainerTrainings = trainingRepository.getTrainerTrainings(username, periodFrom, periodTo, traineeName, trainingType, page, size);
        log.debug("Retrieved all trainees: {}", trainerTrainings);
        return new PageGeneral<>(trainerTrainings.getContent().stream().map(trainingDTOMapper).toList(), trainerTrainings.getNumber(), trainerTrainings.getSize(), trainerTrainings.getTotalElements());
    }

    /**
     * Retrieves all available training types.
     *
     * @return A list of all TrainingType entities.
     */
    @Override
    public List<TrainingType> getTrainingTypes() {
        log.debug("Retrieving all training types");
        return trainingTypeRepository.findAll();
    }

}