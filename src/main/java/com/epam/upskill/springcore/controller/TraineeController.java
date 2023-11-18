package com.epam.upskill.springcore.controller;

import com.epam.upskill.springcore.model.dtos.Page;
import com.epam.upskill.springcore.model.dtos.ResTraineeDTO;
import com.epam.upskill.springcore.model.dtos.TraineeDTO;
import com.epam.upskill.springcore.service.TraineeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @description: Controller class for managing Trainee entities.
 * @date: 10 November 2023 $
 * @time: 1:07 AM 26 $
 * @author: Qudratjon Komilov
 */

@RestController
@RequestMapping("/v1/trainees")
@AllArgsConstructor
public class TraineeController {
    private final TraineeService traineeService;

    /**
     * Create or update a trainee.
     *
     * @param trainee the trainee to be created or updated.
     * @return the created or updated trainee.
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TraineeDTO createOrUpdate(@Valid @RequestBody ResTraineeDTO trainee) {
        return traineeService.createOrUpdate(trainee);
    }

    /**
     * Delete a trainee by ID.
     *
     * @param id the ID of the trainee to delete.
     * @return a response entity indicating the operation's success.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        traineeService.delete(id);
    }

    /**
     * Retrieve a trainee by ID.
     *
     * @param id the ID of the trainee.
     * @return the requested trainee if found.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public TraineeDTO getById(@PathVariable Long id) {
        return traineeService.getById(id);
    }

    /**
     * Get all trainees with optional filtering by date of birth and address.
     *
     * @return a page of trainees.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Page<TraineeDTO> getAll(@PathVariable int page, @PathVariable int size, @PathVariable String name) {
        return traineeService.getByFilter(page, size, name);
    }
}

