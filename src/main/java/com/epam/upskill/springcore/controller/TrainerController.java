package com.epam.upskill.springcore.controller;

import com.epam.upskill.springcore.model.dtos.ResTrainerDTO;
import com.epam.upskill.springcore.model.dtos.TrainerDTO;
import com.epam.upskill.springcore.service.TrainerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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

    /**
     * Endpoint for creating or updating a trainer.
     *
     * @param trainer The trainer data transfer object.
     * @return ResponseEntity containing the created or updated trainer.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TrainerDTO create(@Valid @RequestBody ResTrainerDTO trainer) {
        return trainerService.createOrUpdate(trainer);
    }

    /**
     * Endpoint to retrieve a trainer by their ID.
     *
     * @param id The ID of the trainer to retrieve.
     * @return ResponseEntity containing the requested trainer.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public TrainerDTO getById(@PathVariable Long id) {
        return trainerService.getTrainerById(id);
    }

    /**
     * Endpoint to retrieve all trainers.
     *
     * @return ResponseEntity containing a list of all trainers.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<TrainerDTO> getAll() {
        return trainerService.getAllTrainers();
    }

}

