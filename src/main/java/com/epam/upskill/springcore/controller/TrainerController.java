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
 * Controller class for handling trainer related requests.
 * This class provides RESTful endpoints for managing trainer data,
 * including creating, updating, retrieving specific trainers, and listing trainers
 * based on various filters.
 *
 * @date: 10 November 2023
 * @time: 1:09 AM 07
 * @author: Qudratjon Komilov
 */
@RestController
@RequestMapping("/v1/trainers")
@AllArgsConstructor
@Api(tags = "Trainer Management", value = "Endpoints for managing trainers")
@ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Access Token", paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")})
public class TrainerController {

    private final TrainerService trainerService;


    /**
     * Updates the details of an existing trainer or creates a new one.
     * The method accepts a Trainer Data Transfer Object (DTO) and delegates
     * the processing to the TrainerService.
     *
     * @param trainer A valid Trainer DTO containing the details to be updated or created.
     * @return The updated or newly created Trainer DTO.
     */
    @ApiOperation(value = "Update a Trainer", notes = "Create or update trainer details.")
    @ApiResponses({@ApiResponse(code = 201, message = "Trainer created or updated successfully")})
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Access Token", paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")})
    @PutMapping("/update")
    public TrainerDTO update(@ApiParam(value = "Trainer data transfer object", required = true) @Valid @RequestBody ReqTrainerDTO trainer) {
        return trainerService.update(trainer);
    }

    /**
     * Registers a new trainer in the system.
     * This method is responsible for creating a new trainer account based on the
     * provided trainer registration DTO.
     *
     * @param traineeDTO A valid Trainer registration DTO.
     * @return A response DTO containing login details for the newly registered trainer.
     */
    @ApiOperation(value = "Register a Trainer", notes = "Register a new trainer.")
    @ApiResponses({@ApiResponse(code = 201, message = "Trainer registered successfully")})
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Access Token", paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")})
    public LoginResDTO register(@ApiParam(value = "Trainer data transfer object for registration", required = true) @Valid @RequestBody RestUserTrainerDTO traineeDTO) {
        return trainerService.register(traineeDTO);
    }

    /**
     * Retrieves a trainer's details by their username.
     * This method is used to fetch the details of a specific trainer based on their unique username.
     *
     * @param username The username of the trainer.
     * @return A Trainer DTO containing the details of the requested trainer.
     */
    @ApiOperation(value = "Get Trainer by Username", notes = "Retrieve a trainer by their username.")
    @ApiResponses({@ApiResponse(code = 200, message = "Trainer retrieved successfully")})
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Access Token", paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")})
    @GetMapping("/username/{username}")
    public TrainerDTO getByUsername(@ApiParam(value = "Username of the trainer", required = true) @PathVariable String username) {
        return trainerService.getByUsername(username);
    }


    /**
     * Retrieves trainers who are not assigned to a specific trainee.
     * This method is particularly useful for finding available trainers for a trainee.
     *
     * @param traineeUsername The username of the trainee.
     * @param page            The page number of the requested data.
     * @param size            The number of records per page.
     * @return A paginated list of available Trainer DTOs.
     */
    @ApiOperation(value = "Get Trainers Not Assigned to Trainee", notes = "Retrieve all trainers not assigned to a specific trainee.")
    @ApiResponses({@ApiResponse(code = 200, message = "Trainers retrieved successfully")})
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Access Token", paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")})
    @GetMapping("/not-assigned/{traineeUsername}")
    public PageGeneral<TrainerDTO> getNotAssignedTrainers(@ApiParam(value = "Username of the trainee", required = true) @PathVariable String traineeUsername,

                                                          @ApiParam(value = "Page number", defaultValue = "0") @RequestParam(value = "page", defaultValue = "0") @Min(0) Integer page,

                                                          @ApiParam(value = "Size of the page", defaultValue = "5") @RequestParam(value = "size", defaultValue = "5") @Min(0) Integer size) {
        return trainerService.getNotAssignedTrainers(traineeUsername, page, size);
    }

    /**
     * Activates or deactivates a trainer's account based on the given status.
     * This administrative endpoint allows changing the active status of a trainer's account.
     *
     * @param username The username of the trainer whose status is to be updated.
     * @param isActive The desired active status (true for activate, false for deactivate).
     */
    @ApiOperation(value = "Activate or Deactivate Trainer", notes = "Activate or deactivate a trainer's account.")
    @ApiResponses({@ApiResponse(code = 200, message = "Trainer status updated successfully")})
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({@ApiImplicitParam(name = "Authorization", value = "Access Token", paramType = "header", dataTypeClass = String.class, example = "Bearer access_token")})
    @PatchMapping("/status")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void activate(@ApiParam(value = "Username of the trainer", required = true) @RequestParam String username,

                         @ApiParam(value = "Activation status to set", required = true) @RequestParam boolean isActive) {
        trainerService.activate(username, isActive);
    }


}

