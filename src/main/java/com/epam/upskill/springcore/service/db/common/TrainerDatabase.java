package com.epam.upskill.springcore.service.db.common;

import com.epam.upskill.springcore.model.Trainer;
import com.epam.upskill.springcore.repository.TrainerRepository;
import com.epam.upskill.springcore.service.db.GenericDatabase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @description: Implementation of GenericDatabase for Trainer entity.
 * This class provides database operations for Trainer entity,
 * handling operations both in a repository and a local cache (hash map).
 * @author: Qudratjon Komilov
 * @date: 09 November 2023
 * @time: 7:36 PM
 */
@Service
@AllArgsConstructor
@Slf4j
public class TrainerDatabase implements GenericDatabase<Trainer, Long> {

    private final GenericDatabase<Trainer, Long> traineeDAO;
    private final TrainerRepository traineeRepository;

    /**
     * Saves a Trainer entity.
     * The entity is saved both in the repository and the local cache.
     *
     * @param entity the Trainer entity to be saved
     * @return the saved Trainer entity
     */
    @Override
    public Trainer save(Trainer entity) {
        log.trace("Entering save method with entity: {}", entity);
        Trainer saved = traineeRepository.save(entity);
        log.debug("Trainer saved to PostgreSQL: {}", saved);
        traineeDAO.save(saved);
        log.trace("Exiting save method");
        return saved;
    }

    /**
     * Finds a Trainer by its ID.
     * First checks the local cache, if not found, checks the repository.
     * If found in the repository, it's also saved in the local cache.
     *
     * @param id the ID of the Trainer
     * @return an Optional containing the found Trainer or empty if not found
     */
    @Override
    public Optional<Trainer> findById(Long id) {
        log.trace("Entering findById method with ID: {}", id);

        Optional<Trainer> found = traineeDAO.findById(id);
        log.debug("Trainer search in local hash map for ID {}: {}", id, found);

        if (found.isEmpty()) {
            found = traineeRepository.findById(id);
            log.debug("Trainer search in PostgreSQL for ID {}: {}", id, found);

            found.ifPresent(trainer -> {
                traineeDAO.save(trainer);
                log.debug("Trainer saved to local hash map after PostgreSQL fetch: {}", trainer);
            });
        }
        log.trace("Exiting findById method");

        return found;
    }

    /**
     * Deletes a Trainer by its ID.
     * The entity is deleted from both the repository and the local cache.
     *
     * @param id the ID of the Trainer to be deleted
     */
    @Override
    public void deleteById(Long id) {
        traineeRepository.deleteById(id);
        log.debug("Trainer deleted from PostgreSQL with ID: {}", id);
        traineeDAO.deleteById(id);
        log.debug("Trainer deleted from local hash map with ID: {}", id);
    }

    /**
     * Finds all Trainer entities.
     * First checks the local cache, if empty, fetches from the repository and updates the cache.
     *
     * @return a List of Trainer entities
     */
    @Override
    public List<Trainer> findAll() {
        List<Trainer> all = traineeDAO.findAll();
        log.debug("Attempt to find all Trainers in local hash map");

        if (all.isEmpty()) {
            log.debug("No Trainers found in local hash map, checking PostgreSQL");
            all = traineeRepository.findAll();

            for (Trainer trainer : all) {
                traineeDAO.save(trainer);
            }
            log.debug("Trainers from PostgreSQL saved to local hash map: {}", all);
        }
        return all;
    }
}
