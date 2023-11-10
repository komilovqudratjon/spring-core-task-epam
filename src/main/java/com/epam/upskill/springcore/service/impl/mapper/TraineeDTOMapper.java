package com.epam.upskill.springcore.service.impl.mapper;


import com.epam.upskill.springcore.model.DTOs.TraineeDTO;
import com.epam.upskill.springcore.model.Trainee;
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
public class TraineeDTOMapper implements Function<Trainee, TraineeDTO> {
    @Override
    public TraineeDTO apply(Trainee trainee) {
        return new TraineeDTO(
                trainee.getId(),
                trainee.getDateOfBirth(),
                trainee.getAddress(),
                new UserDTOMapper().apply(trainee.getUser())
        );
    }
}
