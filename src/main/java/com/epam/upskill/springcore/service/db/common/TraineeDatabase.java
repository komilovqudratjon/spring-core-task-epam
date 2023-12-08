package com.epam.upskill.springcore.service.db.common;

import com.epam.upskill.springcore.model.Trainee;
import com.epam.upskill.springcore.model.Trainer;
import com.epam.upskill.springcore.model.Users;
import com.epam.upskill.springcore.repository.TraineeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class TraineeDatabase {

    private final TraineeRepository traineeRepository;
    private final TrainerDatabase trainerDatabase;
    private final TrainingDatabase trainingDatabase;
    private final UserDatabase userDatabase;

    public Trainee save(Trainee entity) {
        log.trace("Saving trainee: {}", entity);
        Trainee saved = traineeRepository.save(entity);
        log.debug("Trainee saved: {}", saved);
        return saved;
    }

    public Optional<Trainee> findById(Long id) {
        log.trace("Searching for trainee with ID: {}", id);
        return traineeRepository.findById(id);
    }

    public void deleteById(Long id) {
        log.info("Deleting trainee with ID: {}", id);
        traineeRepository.deleteById(id);
    }

    public List<Trainee> findAll() {
        log.trace("Fetching all trainees");
        return traineeRepository.findAll();
    }

    public Page<Trainee> getByFilter(int page, int size, String search) {
        log.info("Fetching all trainees");
        return traineeRepository.findByUserFirstNameContainingOrUserLastNameContaining(search, search, Pageable.ofSize(size).withPage(page));

    }


    public Optional<Trainee> findByUsername(String username) {
        // only use traineeRepository
        log.trace("Searching for trainee with username: {}", username);
        Optional<Trainee> byUsername = traineeRepository.findByUserUsername(username);
        if (byUsername.isEmpty()) {
            log.warn("Trainee not found with username: {}", username);
            return Optional.empty();
        }
        return byUsername;
    }

    @Transactional
    public void deleteByUsername(String username) {
        log.info("Deleting trainee with username: {}", username);
        Optional<Trainee> byUserUsername = traineeRepository.findByUserUsername(username);

        if (byUserUsername.isPresent()) {
            Trainee trainee = byUserUsername.get();
            Users user = trainee.getUser();

            traineeRepository.deleteByTrainerTraineeById(user.getId());
            trainingDatabase.deleteByTraineeId(trainee.getId());
            traineeRepository.deleteId(trainee.getId());
//            userDatabase.deleteById(user.getId());

        } else {
            log.info("No trainee found with username: {}", username);
        }
    }

    public List<Trainer> addTrainers(String traineeUsername, List<String> trainerUsername) {
        List<Trainer> trainerList = trainerDatabase.findAllByUserUsernameIn(trainerUsername);
        trainerList.forEach(trainer -> {
            trainer.getTrainees().add(traineeRepository.findByUserUsername(traineeUsername).orElseThrow());
            trainerDatabase.save(trainer);
        });
        return trainerList;
    }

    public void activate(String username, boolean isActive) {
        traineeRepository.findByUserUsername(username).ifPresent(trainee -> {
            trainee.setIsActive(isActive);
            traineeRepository.save(trainee);
        });
    }
}
