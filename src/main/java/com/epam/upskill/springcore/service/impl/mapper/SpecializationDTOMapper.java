package com.epam.upskill.springcore.service.impl.mapper;


import com.epam.upskill.springcore.model.dtos.SpecializationDTO;
import com.epam.upskill.springcore.model.Specialization;
import org.springframework.stereotype.Service;

import java.util.function.Function;

/**
 * @description: Mapper class for SpecializationDTO.
 * @date: 09 November 2023 $
 * @time: 3:37 PM 25 $
 * @author: Qudratjon Komilov
 */
@Service
public class SpecializationDTOMapper implements Function<Specialization, SpecializationDTO> {

    @Override
    public SpecializationDTO apply(Specialization specialization) {
        return new SpecializationDTO(
                specialization.getId(),
                specialization.getSpecializationName()
        );
    }
}
