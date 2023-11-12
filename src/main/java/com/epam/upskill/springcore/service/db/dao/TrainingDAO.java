package com.epam.upskill.springcore.service.db.dao;

import com.epam.upskill.springcore.exception.InvalidException;
import com.epam.upskill.springcore.model.Training;
import com.epam.upskill.springcore.service.db.GenericDatabase;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @description:  DAO class for Training entity.
 * @date: 09 November 2023 $
 * @time: 5:22 PM 28 $
 * @author: Qudratjon Komilov
 */


@Service
public class TrainingDAO implements GenericDatabase<Training, Long> {

    private final Map<Long, Training> trainingSessions = new HashMap<>();

    /**
     * Saves a training session to the database.
     *
     * @param training The training session to save.
     * @return The saved training session.
     * @throws InvalidException If the training's ID is null.
     */
    @Override
    public Training save(Training training) {
        validateTraining(training);
        trainingSessions.put(training.getId(), training);
        return training;
    }

    private void validateTraining(Training training) {
        if (training.getId() == null) {
            throw new InvalidException("Training id cannot be null");
        }
    }

    /**
     * Finds a training session by ID.
     *
     * @param id The ID of the training session.
     * @return An Optional containing the training session, if found.
     */
    @Override
    public Optional<Training> findById(Long id) {
        return Optional.ofNullable(trainingSessions.get(id));
    }

    /**
     * Deletes a training session by ID.
     *
     * @param id The ID of the training session to delete.
     */
    @Override
    public void deleteById(Long id) {
        trainingSessions.remove(id);
    }

    /**
     * Finds all training sessions.
     *
     * @return A list of all training sessions.
     */
    @Override
    public List<Training> findAll() {
        return new ArrayList<>(trainingSessions.values());
    }
}
