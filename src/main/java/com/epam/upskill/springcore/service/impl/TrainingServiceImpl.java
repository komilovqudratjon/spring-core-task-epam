package com.epam.upskill.springcore.service.impl;

import com.epam.upskill.springcore.model.DTOs.TrainingDTO;
import com.epam.upskill.springcore.model.Training;
import com.epam.upskill.springcore.service.TrainingService;
import com.epam.upskill.springcore.service.dbService.Specifications.TrainingSpecifications;
import com.epam.upskill.springcore.service.dbService.common.TrainingDB;
import com.epam.upskill.springcore.service.impl.mapper.TrainingDTOMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @className: TrainingServiceImpl  $
 * @description: TODO
 * @date: 08 November 2023 $
 * @time: 5:43 AM 47 $
 * @author: Qudratjon Komilov
 */


@Service
@AllArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final TrainingDB trainerRepository;

    private final TrainingDTOMapper trainerDTOMapper;

    @Override
    public TrainingDTO createOrUpdateTraining(Training trainer) {
        return trainerDTOMapper.apply(trainerRepository.save(trainer));
    }

    @Override
    public TrainingDTO getTrainingById(Long id) {
        Optional<Training> trainer = trainerRepository.findById(id);
        return trainerDTOMapper.apply(trainer.orElseThrow());
    }

    @Override
    public List<TrainingDTO> getAllTrainings() {
        return trainerRepository.findAll().stream().map(trainerDTOMapper).toList();
    }

    @Override
    public Page<TrainingDTO> getTrainingsByFilter(Pageable pageable, TrainingSpecifications trainingSpecifications) {
        Page<Training> trainings = trainerRepository.findAll(trainingSpecifications, pageable);
        return trainings.map(trainerDTOMapper);
    }

}

