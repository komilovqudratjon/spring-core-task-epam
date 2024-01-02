package com.epam.upskill.springcore.controller;

import com.epam.upskill.springcore.model.dtos.*;
import com.epam.upskill.springcore.service.TraineeService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller class for managing Trainee entities.
 * This class handles HTTP requests related to Trainee operations such as registration,
 * update, delete, and retrieval of Trainee details.
 *
 * @description: Controller class for managing Trainee entities.
 * @date: 10 November 2023
 * @time: 1:07 AM 26
 * @author: Qudratjon Komilov
 */
@RestController
@RequestMapping("/v1/trainees")
@AllArgsConstructor
@Api(tags = "Trainee Management", value = "Endpoints for managing trainees")
@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Access Token", paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")})
public class TraineeController {

    private final TraineeService traineeService;

    /**
     * Updates an existing trainee's details.
     *
     * @param trainee The trainee object to be updated, provided in the request body.
     * @return Updated TraineeDTO object.
     */
    @ApiOperation(value = "Update a trainee", notes = "Updates an existing trainee's details")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Successfully updated the trainee"), @ApiResponse(code = 400, message = "Request body not valid")})
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Access Token", paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")})
    public TraineeDTO updateTrainee(@ApiParam(value = "Trainee object to be updated", required = true) @Valid @RequestBody ReqTraineeDTO trainee) {
        return traineeService.update(trainee);
    }

    /**
     * Registers a new trainee and returns login details.
     *
     * @param traineeDTO The trainee registration details.
     * @return Login response with trainee details.
     */
    @ApiOperation(value = "Register a new trainee", notes = "Registers a trainee and returns login details")
    @ApiResponse(code = 201, message = "Trainee successfully registered")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.OK)
    public LoginResDTO register(@ApiParam(value = "Trainee registration details", required = true) @Valid @RequestBody ReqUserTraineeDTO traineeDTO) {
        return traineeService.register(traineeDTO);
    }

    /**
     * Deletes a trainee based on their username.
     *
     * @param username The username of the trainee to delete.
     */
    @ApiOperation(value = "Delete a trainee by username", notes = "Deletes a trainee based on their username")
    @ApiResponse(code = 204, message = "Trainee successfully deleted")
    @DeleteMapping("/username/{username}")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Access Token", paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")})
    public void deleteByUsername(@ApiParam(value = "Username of the trainee to delete", required = true) @PathVariable String username) {
        traineeService.deleteByUsername(username);
    }


    /**
     * Retrieves a trainee's profile based on their username.
     *
     * @param username The username of the trainee.
     * @return TraineeDTO containing the profile of the trainee.
     */
    @ApiOperation(value = "Get Trainee profile by username", notes = "Retrieves a trainee's profile based on their username")
    @ApiResponses({@ApiResponse(code = 200, message = "Trainee profile retrieved successfully"), @ApiResponse(code = 404, message = "Trainee not found")})
    @GetMapping("/username/{username}")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Access Token", paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")})
    public TraineeDTO getByUsername(@ApiParam(value = "Username of the trainee", required = true) @PathVariable String username) {
        return traineeService.getByUsername(username);
    }

    /**
     * Adds trainers to a trainee's list based on usernames.
     *
     * @param traineeUsername The username of the trainee to update.
     * @param trainerUsername A list of trainer usernames to add to the trainee's trainers list.
     * @return List of TrainerDTO representing the updated trainers list.
     */
    @ApiOperation(value = "Update Trainee's trainers list", notes = "Adds trainers to a trainee's list based on usernames")
    @ApiResponses({@ApiResponse(code = 200, message = "Trainers list updated successfully"), @ApiResponse(code = 404, message = "Trainee or trainer not found")})
    @PutMapping("/add-trainer")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Access Token", paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")})
    public List<TrainerDTO> addTrainers(@ApiParam(value = "Username of the trainee to update", required = true) @RequestParam String traineeUsername, @ApiParam(value = "List of trainer usernames to add", required = true) @RequestParam List<String> trainerUsername) {
        return traineeService.addTrainers(traineeUsername, trainerUsername);
    }

    /**
     * Changes the active status of a trainee.
     * This method is accessible only to users with 'ROLE_ADMIN'.
     *
     * @param username The username of the trainee.
     * @param isActive The boolean flag to activate or deactivate the trainee.
     */
    @ApiOperation(value = "Activate or deactivate a trainee", notes = "Changes the active status of a trainee")
    @ApiResponses({@ApiResponse(code = 200, message = "Trainee's status updated successfully"), @ApiResponse(code = 404, message = "Trainee not found")})
    @PatchMapping("/active-status")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Access Token", paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")})
    public void setActiveStatus(@ApiParam(value = "Username of the trainee to activate or deactivate", required = true) @RequestParam String username, @ApiParam(value = "Boolean flag to activate or deactivate the trainee", required = true) @RequestParam boolean isActive) {
        traineeService.setActiveStatus(username, isActive);
    }
}

