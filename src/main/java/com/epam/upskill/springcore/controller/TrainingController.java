package com.epam.upskill.springcore.controller;

import com.epam.upskill.springcore.model.DTOs.TrainingDTO;
import com.epam.upskill.springcore.model.Training;
import com.epam.upskill.springcore.service.TrainingService;
import com.epam.upskill.springcore.service.dbService.Specifications.TrainingSpecifications;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @className: TrainingController  $
 * @description: TODO
 * @date: 10 November 2023 $
 * @time: 1:10 AM 30 $
 * @author: Qudratjon Komilov
 */


@RestController
@RequestMapping("/api/v1/trainings")
@AllArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;

    @PostMapping
    public ResponseEntity<TrainingDTO> createTraining(@RequestBody Training training) {
        TrainingDTO createdTraining = trainingService.createOrUpdateTraining(training);
        return ResponseEntity.ok(createdTraining);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingDTO> getTrainingById(@PathVariable Long id) {
        TrainingDTO trainingDTO = trainingService.getTrainingById(id);
        return ResponseEntity.ok(trainingDTO);
    }

    @GetMapping
    public ResponseEntity<List<TrainingDTO>> getAllTrainings() {
        List<TrainingDTO> trainings = trainingService.getAllTrainings();
        return ResponseEntity.ok(trainings);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<TrainingDTO>> getAllTrainings(Pageable pageable,
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
        Page<TrainingDTO> trainingPage = trainingService.getTrainingsByFilter(pageable, trainingSpecifications);
        return ResponseEntity.ok(trainingPage);
    }
}
