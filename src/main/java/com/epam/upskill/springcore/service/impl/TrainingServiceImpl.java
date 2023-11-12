package com.epam.upskill.springcore.service.impl;

import com.epam.upskill.springcore.model.dtos.ResTrainingDTO;
import com.epam.upskill.springcore.model.dtos.TrainingDTO;
import com.epam.upskill.springcore.model.Training;
import com.epam.upskill.springcore.repository.SpecializationRepository;
import com.epam.upskill.springcore.repository.TrainingTypeRepository;
import com.epam.upskill.springcore.service.TrainingService;
import com.epam.upskill.springcore.service.db.specifications.TrainingSpecifications;
import com.epam.upskill.springcore.service.db.common.TraineeDatabase;
import com.epam.upskill.springcore.service.db.common.TrainerDatabase;
import com.epam.upskill.springcore.service.db.common.TrainingDatabase;
import com.epam.upskill.springcore.service.impl.mapper.TrainingDTOMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
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
    private final SpecializationRepository specializationRepository;
    private final TrainingTypeRepository trainingTypeRepository;

    /**
     * Creates or updates a Training entity.
     *
     * @param training the Training entity to create or update
     * @return the created or updated TrainingDTO
     */
    @Override
    public TrainingDTO createOrUpdateTraining(ResTrainingDTO training) {
        log.debug("Request to create or update training: {}", training);
        Training trainer = new Training();
        trainer.setId(training.id());
        trainer.setTrainer(trainerRepository.findById(training.trainerId()).orElseThrow(() -> {
            log.error("Trainer not found by id: {}", training.trainerId());
            return new EntityNotFoundException("Trainer not found by id: " + training.trainerId());
        }));
        trainer.setTrainee(traineeRepository.findById(training.traineeId()).orElseThrow(() -> {
            log.error("Trainee not found by id: {}", training.traineeId());
            return new EntityNotFoundException("Trainee not found by id: " + training.traineeId());
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
     * Retrieves a Training by its ID.
     *
     * @param id the ID of the training to retrieve
     * @return the TrainingDTO
     * @throws EntityNotFoundException if the training is not found
     */
    @Override
    public TrainingDTO getTrainingById(Long id) {
        log.debug("Request to retrieve training by id: {}", id);
        Optional<Training> training = trainingRepository.findById(id);

        return trainingDTOMapper.apply(training.orElseThrow(() -> {
            log.error("Trainer not found by id: {}", id);
            return new EntityNotFoundException("Trainer not found by id: " + id);
        }));
    }

    /**
     * Retrieves all Trainers.
     *
     * @return a list of TrainingDTOs
     */
    @Override
    public List<TrainingDTO> getAllTrainings() {
        log.debug("Request to retrieve all trainings");
        List<TrainingDTO> list = trainingRepository.findAll().stream().map(trainingDTOMapper).toList();
        log.info("All trainings retrieved successfully");
        return list;
    }

    /**
     * Retrieves all Trainers based on the given filter.
     *
     * @param pageable               the pagination information
     * @param trainingSpecifications the filter specifications
     * @return a page of TrainingDTOs
     */
    @Override
    public Page<TrainingDTO> getTrainingsByFilter(Pageable pageable, TrainingSpecifications trainingSpecifications) {
        log.debug("Request to retrieve trainings by filter");
        Page<Training> trainings = trainingRepository.findAll(trainingSpecifications, pageable);
        log.info("Trainers retrieved by filter successfully");
        return trainings.map(trainingDTOMapper);
    }

}