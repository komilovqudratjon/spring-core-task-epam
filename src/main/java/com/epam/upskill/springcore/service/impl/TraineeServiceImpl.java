package com.epam.upskill.springcore.service.impl;

import com.epam.upskill.springcore.model.DTOs.TraineeDTO;
import com.epam.upskill.springcore.model.Trainee;
import com.epam.upskill.springcore.service.dbService.Specifications.TraineeSpecifications;
import com.epam.upskill.springcore.service.TraineeService;
import com.epam.upskill.springcore.service.dbService.common.TraineeDB;
import com.epam.upskill.springcore.service.impl.mapper.TraineeDTOMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

/**
 * @className: TraineeServiceImpl  $
 * @description: TODO
 * @date: 08 November 2023 $
 * @time: 5:43 AM 02 $
 * @author: Qudratjon Komilov
 */


@Service
@AllArgsConstructor
public class TraineeServiceImpl implements TraineeService {

    private final TraineeDB traineeRepository;

    private final TraineeDTOMapper traineeDTOMapper;

    @Override
    public TraineeDTO createOrUpdateTrainee(Trainee trainee) {
        return traineeDTOMapper.apply(traineeRepository.save(trainee));
    }

    @Override
    public void deleteTrainee(Long id) {
        traineeRepository.deleteById(id);
    }

    @Override
    public TraineeDTO getTraineeById(Long id) {
        Optional<Trainee> byId = traineeRepository.findById(id);
        return byId.map(traineeDTOMapper).orElse(null);
    }

    @Override
    public Page<TraineeDTO> getAllTrainees(Pageable pageable, Date dateOfBirth, String address) {
        Page<Trainee> trainees = traineeRepository.findAll(new TraineeSpecifications(dateOfBirth, address), pageable);
        return trainees.map(traineeDTOMapper);
    }

}

