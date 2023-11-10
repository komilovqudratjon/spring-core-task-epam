package com.epam.upskill.springcore.controller;

import com.epam.upskill.springcore.model.DTOs.TraineeDTO;
import com.epam.upskill.springcore.model.Trainee;
import com.epam.upskill.springcore.service.TraineeService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @className: TraineeController  $
 * @description: TODO
 * @date: 10 November 2023 $
 * @time: 1:07 AM 26 $
 * @author: Qudratjon Komilov
 */

@CrossOrigin
@RestController
@RequestMapping("/api/v1/trainees")
@AllArgsConstructor
public class TraineeController {

    private final TraineeService traineeService;

    @PostMapping
    public ResponseEntity<TraineeDTO> createOrUpdateTrainee(@RequestBody Trainee trainee) {
        TraineeDTO result = traineeService.createOrUpdateTrainee(trainee);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainee(@PathVariable Long id) {
        traineeService.deleteTrainee(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TraineeDTO> getTraineeById(@PathVariable Long id) {
        TraineeDTO traineeDTO = traineeService.getTraineeById(id);
        return traineeDTO != null ? ResponseEntity.ok(traineeDTO) : ResponseEntity.notFound().build();
    }

    @GetMapping
    public ResponseEntity<Page<TraineeDTO>> getAllTrainees(Pageable pageable,
                                                           @RequestParam(required = false) Date dateOfBirth,
                                                           @RequestParam(required = false) String address) {
        Page<TraineeDTO> page = traineeService.getAllTrainees(pageable, dateOfBirth, address);
        return ResponseEntity.ok(page);
    }
}

