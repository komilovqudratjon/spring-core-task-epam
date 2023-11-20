package com.epam.upskill.springcore.controller;

import com.epam.upskill.springcore.model.dtos.Page;
import com.epam.upskill.springcore.model.dtos.ResTrainerDTO;
import com.epam.upskill.springcore.model.dtos.TrainerDTO;
import com.epam.upskill.springcore.service.TrainerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

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
        return trainerService.getById(id);
    }

    /**
     * Endpoint to retrieve all trainers.
     *
     * @param page   The page number.
     * @param size   The page size.
     * @param search The search string.
     * @return ResponseEntity containing the requested trainers.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<TrainerDTO> getByFilter(
            @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page,
            @RequestParam(value = "size", defaultValue = "5") @Min(0) Integer size,
            @RequestParam(required = false, name = "search") String search) {
        return trainerService.getByFilter(page, size, search);
    }


    /**
     * Select Trainer profile by username.
     *
     * @param username The username of the trainer to retrieve.
     * @return ResponseEntity containing the requested trainer.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/username/{username}")
    public TrainerDTO getByUsername(@PathVariable String username) {
        return trainerService.getByUsername(username);
    }

    /**
     * Get not assigned on specific trainee active trainers list
     *
     * @param traineeId The trainee id
     * @return ResponseEntity containing the requested trainers.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/not-assigned/{traineeId}")
    public Page<TrainerDTO> getNotAssignedTrainers(@PathVariable Long traineeId) {
        return trainerService.getNotAssignedTrainers(traineeId);
    }


}

