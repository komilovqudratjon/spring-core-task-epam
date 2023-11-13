package com.epam.upskill.springcore.controller;

import com.epam.upskill.springcore.model.dtos.ResTrainingDTO;
import com.epam.upskill.springcore.model.dtos.TrainingDTO;
import com.epam.upskill.springcore.service.TrainingService;
import com.epam.upskill.springcore.service.db.specifications.TrainingSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
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

    /**
     * Retrieves trainings based on provided filters.
     *
     * @param pageable         Pagination details.
     * @param traineeId        Optional filter by trainee ID.
     * @param trainerId        Optional filter by trainer ID.
     * @param trainingName     Optional filter by training name.
     * @param trainingTypeId   Optional filter by training type ID.
     * @param trainingDate     Optional filter by training date.
     * @param trainingDuration Optional filter by training duration.
     * @return ResponseEntity containing a page of TrainingDTOs.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/search")
    public Page<TrainingDTO> getAllTrainings(Pageable pageable,
                                             @RequestParam(required = false) Long traineeId,
                                             @RequestParam(required = false) Long trainerId,
                                             @RequestParam(required = false) String trainingName,
                                             @RequestParam(required = false) Long trainingTypeId,
                                             @RequestParam(required = false) Date trainingDate,
                                             @RequestParam(required = false) Integer trainingDuration) {
        TrainingSpecifications trainingSpecifications = TrainingSpecifications.builder()
                .traineeId(traineeId)
                .trainerId(trainerId)
                .trainingName(trainingName)
                .trainingTypeId(trainingTypeId)
                .trainingDate(trainingDate)
                .trainingDuration(trainingDuration)
                .build();
        return trainingService.getByFilter(pageable, trainingSpecifications);
    }
}