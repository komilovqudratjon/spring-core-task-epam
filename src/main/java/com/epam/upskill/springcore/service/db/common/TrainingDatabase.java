package com.epam.upskill.springcore.service.db.common;

import com.epam.upskill.springcore.model.Training;
import com.epam.upskill.springcore.repository.TrainingRepository;
import com.epam.upskill.springcore.service.db.specifications.TrainingSpecifications;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @description: Service class for managing Training entities.
 * This class provides functionalities to save, find, delete, and list Training entities
 * by interacting with both a DAO (Data Access Object) and a JPA repository.
 * @date: 09 November 2023 $
 * @time: 7:36 PM 17 $
 * @author: Qudratjon Komilov
 */


@Service
@AllArgsConstructor
@Slf4j
public class TrainingDatabase {

    private final TrainingRepository trainingRepository;

    /**
     * Saves a Training entity.
     * The method first saves the entity using the JPA repository,
     * then saves the same entity in the DAO, and logs the process.
     *
     * @param entity The Training entity to be saved.
     * @return The saved Training entity.
     */
    public Training save(Training entity) {
        log.debug("Saving a Training entity");
        Training savedEntity = trainingRepository.save(entity);
        log.info("Training saved to PostgreSQL: {}", savedEntity);
        return savedEntity;
    }

    /**
     * Finds a Training entity by its ID.
     * It first attempts to find the entity in the DAO;
     * if not found, it searches the JPA repository.
     * If found in the repository, it is then saved back to the DAO.
     *
     * @param id The ID of the Training entity to find.
     * @return An Optional containing the found Training entity or empty if not found.
     */
    public Optional<Training> findById(Long id) {
        log.trace("Attempting to find Training by ID: {}", id);
        return trainingRepository.findById(id);
    }

    /**
     * Deletes a Training entity by trainee ID.
     */
    public void deleteByTraineeId(Long id) {
        log.info("Deleting Training entity with ID: {}", id);
        trainingRepository.deleteByTraineeId(id);
    }

    /**
     * get all trainings by trainee id
     */
    public List<Training> findAllByTraineeId(Long id) {
        log.info("Fetching all trainings by trainee id: {}", id);
        return trainingRepository.findAllByTraineeId(id);
    }

    /**
     * Finds all Training entities.
     * Initially fetches entities from the DAO; if none found, it fetches from the repository.
     * All fetched entities are saved back to the DAO.
     *
     * @return A list of all Training entities.
     */
    public List<Training> findAll() {
        log.debug("Fetching all Training entities");
        return trainingRepository.findAll();
    }

    public Page<Training> getTraineeTrainings(String username, Date periodFrom, Date periodTo, String trainerName, String trainingType, int page, int size) {
        return trainingRepository.findAll(TrainingSpecifications.builder()
                .usernameTrainee(username)
                .periodFrom(periodFrom)
                .periodTo(periodTo)
                .trainerName(trainerName)
                .trainingType(trainingType)
                .build(), PageRequest.of(page, size));
    }

    public Page<Training> getTrainerTrainings(String username, Date periodFrom, Date periodTo, String traineeName, String trainingType, int page, int size) {
        return trainingRepository.findAll(TrainingSpecifications.builder()
                .usernameTrainer(username)
                .periodFrom(periodFrom)
                .periodTo(periodTo)
                .traineeName(traineeName)
                .trainingType(trainingType)
                .build(), PageRequest.of(page, size));
    }
}
