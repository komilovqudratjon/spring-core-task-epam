package com.epam.upskill.springcore.controller;

import com.epam.upskill.springcore.model.dtos.*;
import com.epam.upskill.springcore.service.TraineeService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * @description: Controller class for managing Trainee entities.
 * @date: 10 November 2023 $
 * @time: 1:07 AM 26 $
 * @author: Qudratjon Komilov
 */

@RestController
@RequestMapping("/v1/trainees")
@AllArgsConstructor
@Api(tags = "Trainee Management", description = "Endpoints for managing trainees")
public class TraineeController {
    private final TraineeService traineeService;
    @ApiOperation(value = "Update a trainee", notes = "Updates an existing trainee's details")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully updated the trainee"),
            @ApiResponse(code = 400, message = "Request body not valid")
    })
    @PutMapping("/update")
    public TraineeDTO updateTrainee(@ApiParam(value = "Trainee object to be updated", required = true) @Valid @RequestBody ReqTraineeDTO trainee) {
        return traineeService.update(trainee);
    }

    @ApiOperation(value = "Register a new trainee", notes = "Registers a trainee and returns login details")
    @ApiResponse(code = 201, message = "Trainee successfully registered")
    @PostMapping("/register")
    public LoginResDTO register(@ApiParam(value = "Trainee registration details", required = true) @Valid @RequestBody ReqUserTraineeDTO traineeDTO) {
        return traineeService.register(traineeDTO);
    }

    @ApiOperation(value = "Delete a trainee by username", notes = "Deletes a trainee based on their username")
    @ApiResponse(code = 204, message = "Trainee successfully deleted")
    @DeleteMapping("/username/{username}")
    public void deleteByUsername(@ApiParam(value = "Username of the trainee to delete", required = true) @PathVariable String username) {
        traineeService.deleteByUsername(username);
    }

    @ApiOperation(value = "Get trainees with optional filters", notes = "Retrieves a page of trainees with optional filtering by criteria like date of birth and address")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Page of trainees retrieved successfully")
    })
    @GetMapping
    public PageGeneral<TraineeDTO> getByFilter(
            @ApiParam(value = "Page number for pagination")
            @RequestParam(value = "page", defaultValue = "1") @Min(0) Integer page,

            @ApiParam(value = "Page size for pagination")
            @RequestParam(value = "size", defaultValue = "5") @Min(0) Integer size,

            @ApiParam(value = "Search criteria for filtering trainees", required = false)
            @RequestParam(required = false, name = "search") String search) {
        return traineeService.getByFilter(page, size, search);
    }

    @ApiOperation(value = "Get Trainee profile by username", notes = "Retrieves a trainee's profile based on their username")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Trainee profile retrieved successfully"),
            @ApiResponse(code = 404, message = "Trainee not found")
    })
    @GetMapping("/username/{username}")
    public TraineeDTO getByUsername(
            @ApiParam(value = "Username of the trainee", required = true)
            @PathVariable String username) {
        return traineeService.getByUsername(username);
    }

    @ApiOperation(value = "Update Trainee's trainers list", notes = "Adds trainers to a trainee's list based on usernames")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Trainers list updated successfully"),
            @ApiResponse(code = 404, message = "Trainee or trainer not found")
    })
    @PutMapping("/add-trainer")
    public List<TrainerDTO> addTrainers(
            @ApiParam(value = "Username of the trainee to update", required = true)
            @RequestParam String traineeUsername,

            @ApiParam(value = "List of trainer usernames to add", required = true)
            @RequestParam List<String> trainerUsername) {
        return traineeService.addTrainers(traineeUsername, trainerUsername);
    }

    @ApiOperation(value = "Activate or deactivate a trainee", notes = "Changes the active status of a trainee")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Trainee's status updated successfully"),
            @ApiResponse(code = 404, message = "Trainee not found")
    })
    @PatchMapping("/status")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void activate(
            @ApiParam(value = "Username of the trainee to activate or deactivate", required = true)
            @RequestParam String username,

            @ApiParam(value = "Boolean flag to activate or deactivate the trainee", required = true)
            @RequestParam boolean isActive) {
        traineeService.activate(username, isActive);
    }
}

