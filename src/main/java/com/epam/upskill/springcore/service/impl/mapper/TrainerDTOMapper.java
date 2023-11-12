package com.epam.upskill.springcore.service.impl.mapper;


import com.epam.upskill.springcore.model.dtos.TrainerDTO;
import com.epam.upskill.springcore.model.Trainer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * @description: Mapper class for TrainerDTO.
 * @date: 09 November 2023 $
 * @time: 3:37 PM 25 $
 * @author: Qudratjon Komilov
 */
@Service
@AllArgsConstructor
public class TrainerDTOMapper implements Function<Trainer, TrainerDTO> {

    private final SpecializationDTOMapper specializationDTOMapper;
    private final UserDTOMapper userDTOMapper;

    @Override
    public TrainerDTO apply(Trainer trainee) {
        return new TrainerDTO(
                trainee.getId(),
                specializationDTOMapper.apply(trainee.getSpecialization()),
                userDTOMapper.apply(trainee.getUser())
        );
    }
}
