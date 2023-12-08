package com.epam.upskill.springcore.service.impl.mapper;


import com.epam.upskill.springcore.model.dtos.TraineeDTO;
import com.epam.upskill.springcore.model.Trainee;
import com.epam.upskill.springcore.model.dtos.TrainerDTO;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * @description: Mapper class for TraineeDTO.
 * @date: 09 November 2023 $
 * @time: 3:37 PM 25 $
 * @author: Qudratjon Komilov
 */
@Service
@AllArgsConstructor
public class TraineeDTOMapper implements Function<Trainee, TraineeDTO> {
    private final UserDTOMapper userDTOMapper;
    private final SpecializationDTOMapper specializationDTOMapper;


    @Override
    public TraineeDTO apply(Trainee trainee) {
        return new TraineeDTO(
                trainee.getId(),
                userDTOMapper.apply(trainee.getUser()),
                trainee.getTrainers().stream().map(trainer -> new TrainerDTO(
                          trainer.getId(),
                          specializationDTOMapper.apply(trainer.getSpecialization()),
                          userDTOMapper.apply(trainer.getUser()),
                          null,
                          trainer.getIsActive())).toList(),
                trainee.getIsActive()
        );
    }
}
