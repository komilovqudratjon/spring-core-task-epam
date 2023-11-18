package com.epam.upskill.springcore.service.db.common;

import com.epam.upskill.springcore.model.Training;
import com.epam.upskill.springcore.repository.TrainingHibernate;
import com.epam.upskill.springcore.service.db.GenericDatabase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
public class TrainingDatabase implements GenericDatabase<Training, Long> {

    private final GenericDatabase<Training, Long> traineeDAO;
    private final TrainingHibernate trainingHibernate;

    /**
     * Saves a Training entity.
     * The method first saves the entity using the JPA repository,
     * then saves the same entity in the DAO, and logs the process.
     *
     * @param entity The Training entity to be saved.
     * @return The saved Training entity.
     */
    @Override
    public Training save(Training entity) {
        log.debug("Saving a Training entity");
        Training savedEntity = trainingHibernate.save(entity);
        traineeDAO.save(savedEntity);
        log.info("Training entity saved with ID: {}", savedEntity.getId());
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
    @Override
    public Optional<Training> findById(Long id) {
        log.trace("Attempting to find Training by ID: {}", id);
        Optional<Training> foundTraining = traineeDAO.findById(id);
        if (foundTraining.isEmpty()) {
            log.debug("Training not found in DAO, searching in repository");
            foundTraining = trainingHibernate.findById(id);
            foundTraining.ifPresent(training -> {
                log.info("Training found in repository, saving to DAO");
                traineeDAO.save(training);
            });
        }
        return foundTraining;
    }

    /**
     * Deletes a Training entity by its ID.
     * The entity is deleted from both the DAO and the JPA repository.
     *
     * @param id The ID of the Training entity to be deleted.
     */
    @Override
    public void deleteById(Long id) {
        log.info("Deleting Training entity with ID: {}", id);
        traineeDAO.deleteById(id);
        trainingHibernate.deleteById(id);
    }

    /**
     * Finds all Training entities.
     * Initially fetches entities from the DAO; if none found, it fetches from the repository.
     * All fetched entities are saved back to the DAO.
     *
     * @return A list of all Training entities.
     */
    @Override
    public List<Training> findAll() {
        log.debug("Fetching all Training entities");
        List<Training> allTrainings = traineeDAO.findAll();
        if (allTrainings.isEmpty()) {
            log.trace("No Training entities found in DAO, fetching from repository");
            allTrainings = trainingHibernate.findAll();
            allTrainings.forEach(training -> {
                log.trace("Saving Training entity back to DAO");
                traineeDAO.save(training);
            });
        }
        return allTrainings;
    }

}
