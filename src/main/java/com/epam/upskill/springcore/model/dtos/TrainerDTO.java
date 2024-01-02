package com.epam.upskill.springcore.model.dtos;

import java.util.List;

/**
 * @className: SpecializationRecord  $
 * @description: TODO
 * @date: 09 November 2023 $
 * @time: 3:00 PM 28 $
 * @author: Qudratjon Komilov
 */
public record TrainerDTO(Long id, SpecializationDTO specialization, UserDTO user, List<TraineeDTO> trainee, Boolean isActive) {
}


