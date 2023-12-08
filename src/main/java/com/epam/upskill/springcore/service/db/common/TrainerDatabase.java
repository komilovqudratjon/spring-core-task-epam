package com.epam.upskill.springcore.service.db.common;

import com.epam.upskill.springcore.model.Trainer;
import com.epam.upskill.springcore.model.Users;
import com.epam.upskill.springcore.repository.TrainerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
public class TrainerDatabase {

    private final TrainerRepository trainerRepository;
    private final UserDatabase userDatabase;

    /**
     * Saves a Trainer entity.
     * The entity is saved both in the repository and the local cache.
     *
     * @param entity the Trainer entity to be saved
     * @return the saved Trainer entity
     */
    public Trainer save(Trainer entity) {
        log.trace("Entering save method with entity: {}", entity);
        Trainer saved = trainerRepository.save(entity);
        log.debug("Trainer saved to PostgreSQL: {}", saved);
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
    public Optional<Trainer> findById(Long id) {
        log.trace("Entering findById method with ID: {}", id);
        return trainerRepository.findById(id);
    }


    /**
     * Finds all Trainer entities.
     * First checks the local cache, if empty, fetches from the repository and updates the cache.
     *
     * @return a List of Trainer entities
     */
    public List<Trainer> findAll() {
        log.debug("Attempt to find all Trainers in local hash map");
        return trainerRepository.findAll();
    }

    public Optional<Trainer> findByUserUsername(String username) {
        return trainerRepository.findByUserUsername(username);
    }

    public Page<Trainer> getByFilter(Integer page, Integer size, String search) {
        return trainerRepository.findByUserFirstNameContainingOrUserLastNameContaining(search, search, Pageable.ofSize(size).withPage(page));
    }

    public Page<Trainer> getNotAssignedTrainers(String username, Integer page, Integer size) {
        return trainerRepository.getNotAssignedTrainers(username, Pageable.ofSize(size).withPage(page));
    }

    public void activate(String username, boolean isActive) {
        trainerRepository.findByUserUsername(username).ifPresent(trainer -> {
            trainer.setIsActive(isActive);
            trainerRepository.save(trainer);
        });
    }

    public List<Trainer> findAllByUserUsernameIn(List<String> usernames) {
        return trainerRepository.findAllByUserUsernameIn(usernames);
    }
}
