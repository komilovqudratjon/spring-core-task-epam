package com.epam.upskill.springcore.service.dbService.DAO;

import com.epam.upskill.springcore.model.Trainee;
import com.epam.upskill.springcore.service.dbService.GenericDB;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @className: TraineeDAO  $
 * @description: TODO
 * @date: 09 November 2023 $
 * @time: 5:22 PM 09 $
 * @author: Qudratjon Komilov
 */


@Service
public class TraineeDAO implements GenericDB<Trainee, Long> {

    private final Map<Long, Trainee> trainees = new HashMap<>();

    @Override
    public Trainee save( Trainee trainee) {
        if (trainee.getId() == null) {
            throw new RuntimeException("Trainee id is null");
        }
        trainees.put(trainee.getId(), trainee);
        return trainee;
    }

    @Override
    public Optional<Trainee> findById(Long id) {
        return Optional.ofNullable(trainees.get(id));
    }

    @Override
    public void deleteById(Long id) {
        trainees.remove(id);
    }

    @Override
    public List<Trainee> findAll() {
        return new ArrayList<>(trainees.values());
    }

    public Map<Long, Trainee> getTrainees() {
        return trainees;
    }
}
