package com.epam.upskill.springcore.service.dbService.DAO;

import com.epam.upskill.springcore.model.Trainer;
import com.epam.upskill.springcore.service.dbService.GenericDB;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @className: TrainerDAO  $
 * @description: TODO
 * @date: 09 November 2023 $
 * @time: 5:21 PM 41 $
 * @author: Qudratjon Komilov
 */


@Service
public class TrainerDAO implements GenericDB<Trainer, Long> {

    private final Map<Long, Trainer> trainers = new HashMap<>();

    @Override
    public Trainer save(Trainer trainer) {
        if (trainer.getId() == null) {
            throw new RuntimeException("Trainer id is null");
        }
        trainers.put(trainer.getId(), trainer);
        return trainer;
    }

    @Override
    public Optional<Trainer> findById(Long id) {
        return Optional.ofNullable(trainers.get(id));
    }

    @Override
    public void deleteById(Long id) {
        trainers.remove(id);
    }

    @Override
    public List<Trainer> findAll() {
        return new ArrayList<>(trainers.values());
    }

    public Map<Long, Trainer> getTrainers() {
        return trainers;
    }
}

