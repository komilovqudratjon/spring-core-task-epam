package com.epam.upskill.springcore.service.db.dao;

import com.epam.upskill.springcore.exception.InvalidException;
import com.epam.upskill.springcore.model.Trainer;
import com.epam.upskill.springcore.service.db.GenericDatabase;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @description: DAO class for Trainer entity.
 * @date: 09 November 2023 $
 * @time: 5:21 PM 41 $
 * @author: Qudratjon Komilov
 */


@Service
public class TrainerDAO implements GenericDatabase<Trainer, Long> {

    private final Map<Long, Trainer> trainers = new HashMap<>();

    /**
     * Saves a trainer to the database.
     *
     * @param trainer The trainer to save.
     * @return The saved trainer.
     * @throws InvalidException If the trainer's ID is null.
     */
    @Override
    public Trainer save(Trainer trainer) {
        validateTrainer(trainer);
        trainers.put(trainer.getId(), trainer);
        return trainer;
    }

    private void validateTrainer(Trainer trainer) {
        if (trainer.getId() == null) {
            throw new InvalidException("Trainer id cannot be null");
        }
    }

    /**
     * Finds a trainer by ID.
     *
     * @param id The ID of the trainer.
     * @return An Optional containing the trainer, if found.
     */
    @Override
    public Optional<Trainer> findById(Long id) {
        return Optional.ofNullable(trainers.get(id));
    }

    /**
     * Deletes a trainer by ID.
     *
     * @param id The ID of the trainer to delete.
     */
    @Override
    public void deleteById(Long id) {
        trainers.remove(id);
    }

    /**
     * Finds all trainers.
     *
     * @return A list of all trainers.
     */
    @Override
    public List<Trainer> findAll() {
        return new ArrayList<>(trainers.values());
    }
}
