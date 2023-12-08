package com.epam.upskill.springcore.controller;

import com.epam.upskill.springcore.model.dtos.*;
import com.epam.upskill.springcore.service.TrainerService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Api(tags = "Trainer Management", description = "Endpoints for managing trainers")
public class TrainerController {

    private final TrainerService trainerService;
    @ApiOperation(value = "Update a Trainer", notes = "Create or update trainer details.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Trainer created or updated successfully")
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/update")
    public TrainerDTO update(
            @ApiParam(value = "Trainer data transfer object", required = true)
            @Valid @RequestBody ReqTrainerDTO trainer) {
        return trainerService.update(trainer);
    }

    @ApiOperation(value = "Register a Trainer", notes = "Register a new trainer.")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Trainer registered successfully")
    })
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public LoginResDTO register(
            @ApiParam(value = "Trainer data transfer object for registration", required = true)
            @Valid @RequestBody RestUserTrainerDTO traineeDTO) {
        return trainerService.register(traineeDTO);
    }

    @ApiOperation(value = "Get Trainers by Filter", notes = "Retrieve trainers based on the given filters.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Trainers retrieved successfully")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public PageGeneral<TrainerDTO> getByFilter(
            @ApiParam(value = "Page number", defaultValue = "0")
            @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page,

            @ApiParam(value = "Size of the page", defaultValue = "5")
            @RequestParam(value = "size", defaultValue = "5") @Min(0) Integer size,

            @ApiParam(value = "Search string for filtering trainers", required = false)
            @RequestParam(required = false, name = "search") String search) {
        return trainerService.getByFilter(page, size, search);
    }

    @ApiOperation(value = "Get Trainer by Username", notes = "Retrieve a trainer by their username.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Trainer retrieved successfully")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/username/{username}")
    public TrainerDTO getByUsername(
            @ApiParam(value = "Username of the trainer", required = true)
            @PathVariable String username) {
        return trainerService.getByUsername(username);
    }

    @ApiOperation(value = "Get Trainers Not Assigned to Trainee", notes = "Retrieve all trainers not assigned to a specific trainee.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Trainers retrieved successfully")
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/not-assigned/{traineeUsername}")
    public PageGeneral<TrainerDTO> getNotAssignedTrainers(
            @ApiParam(value = "Username of the trainee", required = true)
            @PathVariable String traineeUsername,

            @ApiParam(value = "Page number", defaultValue = "0")
            @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page,

            @ApiParam(value = "Size of the page", defaultValue = "5")
            @RequestParam(value = "size", defaultValue = "5") @Min(0) Integer size) {
        return trainerService.getNotAssignedTrainers(traineeUsername, page, size);
    }

    @ApiOperation(value = "Activate or Deactivate Trainer", notes = "Activate or deactivate a trainer's account.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Trainer status updated successfully")
    })
    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/status")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void activate(
            @ApiParam(value = "Username of the trainer", required = true)
            @RequestParam String username,

            @ApiParam(value = "Activation status to set", required = true)
            @RequestParam boolean isActive) {
        trainerService.activate(username, isActive);
    }


}

