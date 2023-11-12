package com.epam.upskill.springcore.service.db.dao;

import com.epam.upskill.springcore.exception.InvalidException;
import com.epam.upskill.springcore.model.Trainee;
import com.epam.upskill.springcore.service.db.GenericDatabase;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @description: DAO class for Trainee entity.
 * @date: 09 November 2023 $
 * @time: 5:22 PM 09 $
 * @author: Qudratjon Komilov
 */

@Service
public class TraineeDAO implements GenericDatabase<Trainee, Long> {

    private final Map<Long, Trainee> trainees = new HashMap<>();

    /**
     * Saves a trainee to the database.
     *
     * @param trainee The trainee to save.
     * @return The saved trainee.
     * @throws InvalidException If the trainee's ID is null.
     */
    @Override
    public Trainee save(Trainee trainee) {
        validateTrainee(trainee);
        trainees.put(trainee.getId(), trainee);
        return trainee;
    }

    private void validateTrainee(Trainee trainee) {
        if (trainee.getId() == null) {
            throw new InvalidException("Trainee id cannot be null");
        }
    }

    /**
     * Finds a trainee by ID.
     *
     * @param id The ID of the trainee.
     * @return An Optional containing the trainee, if found.
     */
    @Override
    public Optional<Trainee> findById(Long id) {
        return Optional.ofNullable(trainees.get(id));
    }

    /**
     * Deletes a trainee by ID.
     *
     * @param id The ID of the trainee to delete.
     */
    @Override
    public void deleteById(Long id) {
        trainees.remove(id);
    }

    /**
     * Finds all trainees.
     *
     * @return A list of all trainees.
     */
    @Override
    public List<Trainee> findAll() {
        return new ArrayList<>(trainees.values());
    }
}
