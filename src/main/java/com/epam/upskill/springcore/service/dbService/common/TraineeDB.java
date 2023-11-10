package com.epam.upskill.springcore.service.dbService.common;

import com.epam.upskill.springcore.model.Trainee;
import com.epam.upskill.springcore.repository.TraineeRepository;
import com.epam.upskill.springcore.service.dbService.DAO.TraineeDAO;
import com.epam.upskill.springcore.service.dbService.GenericDB;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @className: TraineeDB  $
 * @description: TODO
 * @date: 09 November 2023 $
 * @time: 7:36 PM 17 $
 * @author: Qudratjon Komilov
 */
@Service
@AllArgsConstructor
public class TraineeDB implements GenericDB<Trainee, Long> {

    private final TraineeDAO traineeDAO;

    private final TraineeRepository traineeRepository;

    @Override
    public Trainee save(Trainee entity) {
        Trainee save = traineeRepository.save(entity);
        traineeDAO.save(save);
        return save;
    }

    @Override
    public Optional<Trainee> findById(Long aLong) {
        Optional<Trainee> byId = traineeDAO.findById(aLong);
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
    public List<Trainee> findAll() {
        List<Trainee> all = traineeDAO.findAll();
        if (all.isEmpty()) {
            all = traineeRepository.findAll();
            for (Trainee training : all) {
                traineeDAO.save(training);
            }
        }
        return all;
    }

    public Page<Trainee> findAll(Specification<Trainee> spec, Pageable pageable) {
        return traineeRepository.findAll(spec, pageable);
    }

}
