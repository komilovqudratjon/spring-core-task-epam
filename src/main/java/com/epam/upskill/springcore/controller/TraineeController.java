package com.epam.upskill.springcore.controller;

import com.epam.upskill.springcore.model.dtos.ResTraineeDTO;
import com.epam.upskill.springcore.model.dtos.TraineeDTO;
import com.epam.upskill.springcore.service.TraineeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;

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
    @PostMapping
    public ResponseEntity<TraineeDTO> createOrUpdateTrainee(@Valid @RequestBody ResTraineeDTO trainee) {
        TraineeDTO result = traineeService.createOrUpdateTrainee(trainee);
        return ResponseEntity.ok(result);
    }

    /**
     * Delete a trainee by ID.
     *
     * @param id the ID of the trainee to delete.
     * @return a response entity indicating the operation's success.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainee(@PathVariable Long id) {
        traineeService.deleteTrainee(id);
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieve a trainee by ID.
     *
     * @param id the ID of the trainee.
     * @return the requested trainee if found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<TraineeDTO> getTraineeById(@PathVariable Long id) {
        TraineeDTO traineeDTO = traineeService.getTraineeById(id);
        return traineeDTO != null ? ResponseEntity.ok(traineeDTO) : ResponseEntity.notFound().build();
    }

    /**
     * Get all trainees with optional filtering by date of birth and address.
     *
     * @param pageable    pagination information.
     * @param dateOfBirth optional filter for date of birth.
     * @param address     optional filter for address.
     * @return a page of trainees.
     */
    @GetMapping
    public ResponseEntity<Page<TraineeDTO>> getAllTrainees(Pageable pageable,
                                                           @RequestParam(required = false) Date dateOfBirth,
                                                           @RequestParam(required = false) String address) {
        Page<TraineeDTO> page = traineeService.getTraineesByFilter(pageable, dateOfBirth, address);
        return ResponseEntity.ok(page);
    }
}

