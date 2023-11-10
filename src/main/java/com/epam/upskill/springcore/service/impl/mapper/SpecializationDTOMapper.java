package com.epam.upskill.springcore.service.impl.mapper;


import com.epam.upskill.springcore.model.DTOs.SpecializationDTO;
import com.epam.upskill.springcore.model.Specialization;

import java.util.function.Function;

/**
 * @className: TraineeDTOMapper  $
 * @description: TODO
 * @date: 09 November 2023 $
 * @time: 3:37 PM 25 $
 * @author: Qudratjon Komilov
 */
public class SpecializationDTOMapper implements Function<Specialization, SpecializationDTO> {

    @Override
    public SpecializationDTO apply(Specialization specialization) {
        return new SpecializationDTO(
                specialization.getId(),
                specialization.getSpecializationName()
        );
    }
}
