package com.epam.upskill.springcore.controller;

import com.epam.upskill.springcore.model.TrainingType;
import com.epam.upskill.springcore.model.dtos.PageGeneral;
import com.epam.upskill.springcore.model.dtos.ResTrainingDTO;
import com.epam.upskill.springcore.model.dtos.TrainingDTO;
import com.epam.upskill.springcore.service.TrainingService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * TrainingController is responsible for handling HTTP requests related to training operations.
 * It uses TrainingService to perform the business logic associated with these operations.
 *
 * @date: 10 November 2023
 * @time: 1:10 AM 30
 * @author: Qudratjon Komilov
 */

@RestController
@RequestMapping("/v1/trainings")
@AllArgsConstructor
@Api(tags = "Training Management", value = "Endpoints for managing trainings")
public class TrainingController {

    private final TrainingService trainingService;

    /**
     * Creates a new training or updates an existing one.
     *
     * @param training The training data transfer object.
     * @return The created or updated TrainingDTO.
     */
    @ApiOperation(value = "Create or Update a Training", notes = "Creates a new training or updates an existing one.")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Successfully created or updated the training")})
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public TrainingDTO createOrUpdate(@ApiParam(value = "The training data transfer object", required = true) @Valid @RequestBody ResTrainingDTO training) {
        return trainingService.createOrUpdate(training);
    }

    /**
     * Retrieves training records for a specific trainee with optional filters.
     *
     * @param username     The username of the trainee.
     * @param periodFrom   The start date to filter trainings.
     * @param periodTo     The end date to filter trainings.
     * @param trainerName  The name of the trainer to filter by.
     * @param trainingType The type of training to filter by.
     * @param page         The page number for pagination.
     * @param size         The number of records per page for pagination.
     * @return A page of TrainingDTOs for the trainee.
     */
    @ApiOperation(value = "Get Trainee Trainings", notes = "Retrieves training records for a specific trainee.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved the trainee trainings")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/trainee")
    public PageGeneral<TrainingDTO> getTraineeTrainings(@ApiParam(value = "The username of the trainee", required = true) @RequestParam String username,

                                                        @ApiParam(value = "The start date of the period to filter by", required = false, example = "2020-12-01") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date periodFrom,

                                                        @ApiParam(value = "The end date of the period to filter by", required = false, example = "2030-12-31") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date periodTo,

                                                        @ApiParam(value = "The name of the trainer to filter by", required = false) @RequestParam(required = false) String trainerName,

                                                        @ApiParam(value = "The type of the training to filter by", required = false) @RequestParam(required = false) String trainingType,

                                                        @ApiParam(value = "The page number for pagination", required = false, defaultValue = "0") @RequestParam(required = false, defaultValue = "0") int page,

                                                        @ApiParam(value = "The number of records per page for pagination", required = false, defaultValue = "5") @RequestParam(required = false, defaultValue = "5") int size) {
        return trainingService.getTraineeTrainings(username, periodFrom, periodTo, trainerName, trainingType, page, size);
    }

    /**
     * Retrieves all trainings conducted by a specific trainer with optional filters.
     *
     * @param username     The username of the trainer.
     * @param periodFrom   The start date to filter trainings.
     * @param periodTo     The end date to filter trainings.
     * @param traineeName  The name of the trainee to filter by.
     * @param trainingType The type of training to filter by.
     * @param page         The page number for pagination.
     * @param size         The number of records per page for pagination.
     * @return A page of TrainingDTOs conducted by the trainer.
     */
    @ApiOperation(value = "Get Trainer Trainings", notes = "Retrieves all trainings conducted by a specific trainer.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved the trainer trainings")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/trainer")
    public PageGeneral<TrainingDTO> getTrainerTrainings(@ApiParam(value = "The username of the trainer", required = true) @RequestParam String username,

                                                        @ApiParam(value = "The start date of the period to filter by", required = false, example = "2020-12-01") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date periodFrom,

                                                        @ApiParam(value = "The end date of the period to filter by", required = false, example = "2030-12-31") @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date periodTo,

                                                        @ApiParam(value = "The name of the trainee to filter by", required = false) @RequestParam(required = false) String traineeName,

                                                        @ApiParam(value = "The type of the training to filter by", required = false) @RequestParam(required = false) String trainingType,

                                                        @ApiParam(value = "The page number for pagination", required = false, defaultValue = "0") @RequestParam(required = false, defaultValue = "0") int page,

                                                        @ApiParam(value = "The number of records per page for pagination", required = false, defaultValue = "5") @RequestParam(required = false, defaultValue = "5") int size) {
        return trainingService.getTrainerTrainings(username, periodFrom, periodTo, traineeName, trainingType, page, size);
    }

    /**
     * Retrieves a list of all available training types.
     *
     * @return A list of TrainingType.
     */
    @ApiOperation(value = "Get Training Types", notes = "Retrieves all available training types.")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully retrieved the training types")})
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/types")
    public List<TrainingType> getTrainingTypes() {
        return trainingService.getTrainingTypes();
    }
}