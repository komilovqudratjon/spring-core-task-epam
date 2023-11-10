package com.epam.upskill.springcore.service.impl.mapper;


import com.epam.upskill.springcore.model.DTOs.TraineeDTO;
import com.epam.upskill.springcore.model.DTOs.TrainerDTO;
import com.epam.upskill.springcore.model.Trainee;
import com.epam.upskill.springcore.model.Trainer;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * @className: TraineeDTOMapper  $
 * @description: TODO
 * @date: 09 November 2023 $
 * @time: 3:37 PM 25 $
 * @author: Qudratjon Komilov
 */
@Service
public class TrainerDTOMapper implements Function<Trainer, TrainerDTO> {
    @Override
    public TrainerDTO apply(Trainer trainee) {
        return new TrainerDTO(
                trainee.getId(),
                new SpecializationDTOMapper().apply(trainee.getSpecialization()),
                new UserDTOMapper().apply(trainee.getUser())
        );
    }
}
