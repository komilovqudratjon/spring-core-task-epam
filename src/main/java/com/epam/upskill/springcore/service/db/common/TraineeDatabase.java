package com.epam.upskill.springcore.service.db.common;

import com.epam.upskill.springcore.model.Trainee;
import com.epam.upskill.springcore.repository.TraineeRepository;
import com.epam.upskill.springcore.service.db.GenericDatabase;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    private final TraineeRepository traineeRepository;

    /**
     * Saves a trainee entity.
     *
     * @param entity The trainee entity to save.
     * @return The saved trainee entity.
     */
    @Override
    public Trainee save(Trainee entity) {
        log.info("Saving trainee: {}", entity);
        Trainee saved = traineeRepository.save(entity);
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
            byId = traineeRepository.findById(id);
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
        traineeRepository.deleteById(id);
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
            all = traineeRepository.findAll();
            for (Trainee trainee : all) {
                traineeDAO.save(trainee);
            }
        }
        return all;
    }

    /**
     * Finds all trainees matching a specification with pagination.
     *
     * @param spec     The specification to filter trainees.
     * @param pageable Pagination details.
     * @return A page of trainees.
     */
    public Page<Trainee> findAll(Specification<Trainee> spec, Pageable pageable) {
        log.info("Fetching all trainees with specification: {} and pageable: {}", spec, pageable);
        return traineeRepository.findAll(spec, pageable);
    }

}
