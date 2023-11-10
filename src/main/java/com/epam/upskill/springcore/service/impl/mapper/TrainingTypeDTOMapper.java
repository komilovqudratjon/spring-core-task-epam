package com.epam.upskill.springcore.service.impl.mapper;


import com.epam.upskill.springcore.model.DTOs.TrainingTypeDTO;
import com.epam.upskill.springcore.model.TrainingType;
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
public class TrainingTypeDTOMapper implements Function<TrainingType, TrainingTypeDTO> {
    @Override
    public TrainingTypeDTO apply(TrainingType training) {
        return new TrainingTypeDTO(
                training.getId(),
                training.getTrainingTypeName()
        );
    }
}
