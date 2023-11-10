package com.epam.upskill.springcore.service.dbService.common;

import com.epam.upskill.springcore.model.Trainee;
import com.epam.upskill.springcore.model.Training;
import com.epam.upskill.springcore.repository.TrainingRepository;
import com.epam.upskill.springcore.service.dbService.DAO.TrainingDAO;
import com.epam.upskill.springcore.service.dbService.GenericDB;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @className: TrainingDB  $
 * @description: TODO
 * @date: 09 November 2023 $
 * @time: 7:36 PM 17 $
 * @author: Qudratjon Komilov
 */
@Service
@AllArgsConstructor
public class TrainingDB implements GenericDB<Training, Long> {


    private final TrainingDAO traineeDAO;

    private final TrainingRepository traineeRepository;

    @Override
    public Training save(Training entity) {
        Training save = traineeRepository.save(entity);
        traineeDAO.save(save);
        return save;
    }

    @Override
    public Optional<Training> findById(Long aLong) {
        Optional<Training> byId = traineeDAO.findById(aLong);
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
        traineeDAO.deleteById(aLong);
        traineeRepository.deleteById(aLong);
    }

    @Override
    public List<Training> findAll() {
        List<Training> all = traineeDAO.findAll();
        if (all.isEmpty()) {
            all = traineeRepository.findAll();
            for (Training training : all) {
                traineeDAO.save(training);
            }
        }
        return all;
    }

    public Page<Training> findAll(Specification<Training> spec, Pageable pageable) {
        return traineeRepository.findAll(spec, pageable);
    }
}
