package com.epam.upskill.springcore.service.dbService.DAO;

import com.epam.upskill.springcore.model.Training;
import com.epam.upskill.springcore.service.dbService.GenericDB;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @className: TrainingDAO  $
 * @description: TODO
 * @date: 09 November 2023 $
 * @time: 5:22 PM 28 $
 * @author: Qudratjon Komilov
 */


@Service
public class TrainingDAO implements GenericDB<Training, Long> {

    private final Map<Long, Training> trainingSessions = new HashMap<>();

    @Override
    public Training save(Training training) {
        if (training.getId() == null) {
            throw new RuntimeException("Training id is null");
        }
        trainingSessions.put(training.getId(), training);
        return training;
    }

    @Override
    public Optional<Training> findById(Long id) {
        return Optional.ofNullable(trainingSessions.get(id));
    }

    @Override
    public void deleteById(Long id) {
        trainingSessions.remove(id);
    }

    @Override
    public List<Training> findAll() {
        return new ArrayList<>(trainingSessions.values());
    }

    public Map<Long, Training> getTrainingSessions() {
        return trainingSessions;
    }
}

