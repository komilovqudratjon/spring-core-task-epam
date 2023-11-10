package com.epam.upskill.springcore.controller;

import com.epam.upskill.springcore.model.DTOs.TrainerDTO;
import com.epam.upskill.springcore.model.Trainer;
import com.epam.upskill.springcore.service.TrainerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @className: TrainerController  $
 * @description: TODO
 * @date: 10 November 2023 $
 * @time: 1:09 AM 07 $
 * @author: Qudratjon Komilov
 */


@RestController
@RequestMapping("/api/v1/trainers")
@AllArgsConstructor
public class TrainerController {

    private final TrainerService trainerService;


    @PostMapping
    public ResponseEntity<TrainerDTO> createTrainer(@RequestBody Trainer trainer) {
        TrainerDTO createdTrainer = trainerService.createOrUpdateTrainer(trainer);
        return ResponseEntity.ok(createdTrainer);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainerDTO> getTrainerById(@PathVariable Long id) {
        TrainerDTO trainerDTO = trainerService.getTrainerById(id);
        return ResponseEntity.ok(trainerDTO);
    }

    @GetMapping
    public ResponseEntity<List<TrainerDTO>> getAllTrainers() {
        List<TrainerDTO> trainers = trainerService.getAllTrainers();
        return ResponseEntity.ok(trainers);
    }
}

