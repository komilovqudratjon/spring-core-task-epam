package com.epam.upskill.springcore.service.dbService.common;

import com.epam.upskill.springcore.model.Trainer;
import com.epam.upskill.springcore.repository.TrainerRepository;
import com.epam.upskill.springcore.service.dbService.DAO.TrainerDAO;
import com.epam.upskill.springcore.service.dbService.GenericDB;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @className: TrainerDB  $
 * @description: TODO
 * @date: 09 November 2023 $
 * @time: 7:36 PM 17 $
 * @author: Qudratjon Komilov
 */
@Service
@AllArgsConstructor
public class TrainerDB implements GenericDB<Trainer, Long> {
    private final TrainerDAO traineeDAO;

    private final TrainerRepository traineeRepository;

    @Override
    public Trainer save( Trainer entity) {
        Trainer save = traineeRepository.save(entity);
        traineeDAO.save(save);
        return save;
    }

    @Override
    public Optional<Trainer> findById(Long aLong) {
        Optional<Trainer> byId = traineeDAO.findById(aLong);
        if (byId.isEmpty()) {
            byId = traineeRepository.findById(aLong);
            if (byId.isEmpty()) {
                return Optional.empty();
            }
            traineeDAO.save(byId.get());
        }
        return byId;
    }

    @Override
    public void deleteById(Long aLong) {
        traineeRepository.deleteById(aLong);
        traineeDAO.deleteById(aLong);
    }

    @Override
    public List<Trainer> findAll() {
        List<Trainer> all = traineeDAO.findAll();
        if (all.isEmpty()) {
            all = traineeRepository.findAll();
            for (Trainer training : all) {
                traineeDAO.save(training);
            }
        }
        return all;
    }
}
