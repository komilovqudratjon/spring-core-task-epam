package com.epam.upskill.springcore.service.impl;

import com.epam.upskill.springcore.model.DTOs.TrainerDTO;
import com.epam.upskill.springcore.repository.TrainerRepository;
import com.epam.upskill.springcore.model.Trainer;
import com.epam.upskill.springcore.service.TrainerService;
import com.epam.upskill.springcore.service.dbService.common.TrainerDB;
import com.epam.upskill.springcore.service.impl.mapper.TrainerDTOMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @className: TrainerServiceImpl  $
 * @description: TODO
 * @date: 08 November 2023 $
 * @time: 5:43 AM 47 $
 * @author: Qudratjon Komilov
 */


@Service
@AllArgsConstructor
public class TrainerServiceImpl implements TrainerService {

    private final TrainerDB trainerRepository;

    private final TrainerDTOMapper trainerDTOMapper;

    @Override
    public TrainerDTO createOrUpdateTrainer(Trainer trainer) {
        return trainerDTOMapper.apply(trainerRepository.save(trainer));
    }

    @Override
    public TrainerDTO getTrainerById(Long id) {
        return trainerDTOMapper.apply(trainerRepository.findById(id).orElseThrow());
    }

    @Override
    public List<TrainerDTO> getAllTrainers() {
        return trainerRepository.findAll().stream().map(trainerDTOMapper).toList();
    }

}

