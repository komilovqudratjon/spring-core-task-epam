package com.epam.upskill.springcore.controller;

import com.epam.upskill.springcore.model.dtos.ResTrainerDTO;
import com.epam.upskill.springcore.model.dtos.TrainerDTO;
import com.epam.upskill.springcore.service.TrainerService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @description: Controller class for handling trainer related requests.
 * This class provides endpoints for creating, retrieving a specific trainer,
 * and getting all trainers.
 * @date: 10 November 2023 $
 * @time: 1:09 AM 07 $
 * @author: Qudratjon Komilov
 */
@RestController
@RequestMapping("/v1/trainers")
@AllArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;
    private static final Logger logger = LoggerFactory.getLogger(TrainerController.class);

    /**
     * Endpoint for creating or updating a trainer.
     *
     * @param trainer The trainer data transfer object.
     * @return ResponseEntity containing the created or updated trainer.
     */
    @PostMapping
    public ResponseEntity<TrainerDTO> createTrainer(@Valid @RequestBody ResTrainerDTO trainer) {
        logger.info("Request to create/update a trainer: {}", trainer);
        TrainerDTO createdTrainer = trainerService.createOrUpdateTrainer(trainer);
        logger.info("Trainer created/updated successfully: {}", createdTrainer);
        return ResponseEntity.ok(createdTrainer);
    }

    /**
     * Endpoint to retrieve a trainer by their ID.
     *
     * @param id The ID of the trainer to retrieve.
     * @return ResponseEntity containing the requested trainer.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TrainerDTO> getTrainerById(@PathVariable Long id) {
        logger.info("Request to get trainer by ID: {}", id);
        TrainerDTO trainerDTO = trainerService.getTrainerById(id);
        logger.info("Trainer retrieved successfully: {}", trainerDTO);
        return ResponseEntity.ok(trainerDTO);
    }

    /**
     * Endpoint to retrieve all trainers.
     *
     * @return ResponseEntity containing a list of all trainers.
     */
    @GetMapping
    public ResponseEntity<List<TrainerDTO>> getAllTrainers() {
        logger.info("Request to get all trainers");
        List<TrainerDTO> trainers = trainerService.getAllTrainers();
        logger.info("All trainers retrieved successfully");
        return ResponseEntity.ok(trainers);
    }

}

