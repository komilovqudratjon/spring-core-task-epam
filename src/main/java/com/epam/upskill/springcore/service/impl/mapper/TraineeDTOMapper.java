package com.epam.upskill.springcore.service.impl.mapper;


import com.epam.upskill.springcore.model.dtos.TraineeDTO;
import com.epam.upskill.springcore.model.Trainee;
import lombok.AllArgsConstructor;
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

    @Override
    public TraineeDTO apply(Trainee trainee) {
        return new TraineeDTO(
                trainee.getId(),
                trainee.getDateOfBirth(),
                trainee.getAddress(),
                userDTOMapper.apply(trainee.getUser())
        );
    }
}
