package com.epam.upskill.springcore.controller;

import com.epam.upskill.springcore.model.dtos.Page;
import com.epam.upskill.springcore.model.dtos.ResTraineeDTO;
import com.epam.upskill.springcore.model.dtos.TraineeDTO;
import com.epam.upskill.springcore.service.TraineeService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

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
     * Delete a trainee by username.
     *
     * @param username the username of the trainee to delete.
     * @return a response entity indicating the operation's success.
     */
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/username/{username}")
    public void deleteByUsername(@PathVariable String username) {
        traineeService.deleteByUsername(username);
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
    public Page<TraineeDTO> getByFilter(@RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page, @RequestParam(value = "size", defaultValue = "5") @Min(0) Integer size, @RequestParam(required = false, name = "search") String search) {
        return traineeService.getByFilter(page, size, search);
    }

    /**
     * Select Trainee profile by username.
     *
     * @param username The username of the trainer to retrieve.
     * @return ResponseEntity containing the requested trainer.
     */
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/username/{username}")
    public TraineeDTO getByUsername(@PathVariable String username) {
        return traineeService.getByUsername(username);
    }

    /**
     *  Update Tranee's trainers list
     *  @param traineeId The trainee id
     *  @param trainerId The trainer id
     *  @return ResponseEntity containing the requested trainers.
     */
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/add-trainer/{traineeId}/{trainerId}")
    public void addTrainers(@PathVariable Long traineeId, @PathVariable Long trainerId) {
         traineeService.addTrainers(traineeId, trainerId);
    }
}

