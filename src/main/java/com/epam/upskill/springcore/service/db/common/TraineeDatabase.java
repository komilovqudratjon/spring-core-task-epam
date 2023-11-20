package com.epam.upskill.springcore.service.db.common;

import com.epam.upskill.springcore.model.Trainee;
import com.epam.upskill.springcore.model.dtos.Page;
import com.epam.upskill.springcore.repository.TraineeHibernate;
import com.epam.upskill.springcore.service.db.GenericDatabase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @description: Service class for managing Trainee entities.
 * @date: 09 November 2023 $
 * @time: 7:36 PM 17 $
 * @author: Qudratjon Komilov
 */
@Service
@AllArgsConstructor
@Slf4j
public class TraineeDatabase implements GenericDatabase<Trainee, Long> {

    private final GenericDatabase<Trainee, Long> traineeDAO;

    private final TraineeHibernate traineeHibernate;

    /**
     * Saves a trainee entity.
     *
     * @param entity The trainee entity to save.
     * @return The saved trainee entity.
     */
    @Override
    public Trainee save(Trainee entity) {
        log.trace("Saving trainee: {}", entity);
        Trainee saved = traineeHibernate.save(entity);
        traineeDAO.save(saved);
        log.debug("Trainee saved: {}", saved);
        return saved;
    }


    /**
     * Finds a trainee by ID.
     *
     * @param id The ID of the trainee.
     * @return Optional containing the trainee if found, otherwise empty.
     */
    @Override
    public Optional<Trainee> findById(Long id) {
        log.trace("Searching for trainee with ID: {}", id);
        Optional<Trainee> byId = traineeDAO.findById(id);
        if (byId.isEmpty()) {
            log.debug("Trainee not found in DAO, searching in repository for ID: {}", id);
            byId = traineeHibernate.findById(id);
            if (byId.isEmpty()) {
                log.warn("Trainee not found with ID: {}", id);
                return Optional.empty();
            }
            traineeDAO.save(byId.get());
        }
        return byId;
    }

    /**
     * Deletes a trainee by ID.
     *
     * @param id The ID of the trainee to delete.
     */
    @Override
    public void deleteById(Long id) {
        log.info("Deleting trainee with ID: {}", id);
        traineeHibernate.deleteById(id);
        traineeDAO.deleteById(id);
    }

    /**
     * Finds all trainees.
     *
     * @return A list of trainees.
     */
    @Override
    public List<Trainee> findAll() {
        log.trace("Fetching all trainees");
        List<Trainee> all = traineeDAO.findAll();
        if (all.isEmpty()) {
            log.debug("No trainees found in DAO, fetching from repository");
            all = traineeHibernate.findAll();
            for (Trainee trainee : all) {
                traineeDAO.save(trainee);
            }
        }
        return all;
    }

    /**
     * Finds all trainees matching a specification with pagination.
     *
     * @return A page of trainees.
     */
    public Page<Trainee> getByFilter(int page, int size, String search) {
        log.info("Fetching all trainees");
        return traineeHibernate.getByFilter(page, size, search);

    }

    /**
     * Finds a trainee by username.
     *
     * @param username The username of the trainee.
     * @return Optional containing the trainee if found, otherwise empty.
     */
    public Optional<Trainee> findByUsername(String username) {
        // only use traineeHibernate
        log.trace("Searching for trainee with username: {}", username);
        Optional<Trainee> byUsername = traineeHibernate.findByUsername(username);
        if (byUsername.isEmpty()) {
            log.warn("Trainee not found with username: {}", username);
            return Optional.empty();
        }
        return byUsername;
    }

    public void deleteByUsername(String username) {
        log.info("Deleting trainee with username: {}", username);
        traineeHibernate.deleteByUsername(username);
    }

    public void addTrainers(Long traineeId, Long trainerId) {
        traineeHibernate.addTrainers(traineeId, trainerId);
    }
}
