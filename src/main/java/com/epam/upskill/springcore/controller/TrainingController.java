package com.epam.upskill.springcore.controller;

import com.epam.upskill.springcore.model.dtos.ResTrainingDTO;
import com.epam.upskill.springcore.model.dtos.TrainingDTO;
import com.epam.upskill.springcore.service.TrainingService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @description: TrainingController handles all HTTP requests related to training operations.
 * It delegates business logic to TrainingService.
 * @date: 10 November 2023 $
 * @time: 1:10 AM 30 $
 * @author: Qudratjon Komilov
 */

@RestController
@RequestMapping("/v1/trainings")
@AllArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;

    /**
     * Creates or updates a training.
     *
     * @param training The training data transfer object.
     * @return ResponseEntity containing the created or updated TrainingDTO.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TrainingDTO createOrUpdate(@Valid @RequestBody ResTrainingDTO training) {
        return trainingService.createOrUpdate(training);
    }

    /**
     * Retrieves a training by its ID.
     *
     * @param id The ID of the training.
     * @return ResponseEntity containing the TrainingDTO.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public TrainingDTO getById(@PathVariable Long id) {
        return trainingService.getById(id);
    }

    /**
     * Retrieves all trainings.
     *
     * @return ResponseEntity containing a list of TrainingDTOs.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<TrainingDTO> getAllTrainings() {
        return trainingService.getAll();
    }


}