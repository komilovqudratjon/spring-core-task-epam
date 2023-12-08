package com.epam.upskill.springcore.controller;

import com.epam.upskill.springcore.model.dtos.LoginDTO;
import com.epam.upskill.springcore.model.dtos.LoginResDTO;
import com.epam.upskill.springcore.model.dtos.UserDTO;
import com.epam.upskill.springcore.service.UserService;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


/**
 * @description: TODO
 * @date: 13 November 2023 $
 * @time: 11:25 PM 24 $
 * @author: Qudratjon Komilov
 */
@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
@Api(tags = "User Management" ,value = "User Management System", description = "Operations pertaining to user in User Management System")
public class UserController {

    private final UserService userService;


    @PostMapping("/change-password")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Change User Password", notes = "Changes the password for a user")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Password changed successfully"),
            @ApiResponse(code = 400, message = "Invalid user details provided")
    })
    public LoginResDTO changePassword(@ApiParam(value = "User login information", required = true) @RequestBody LoginDTO loginDTO) {
        return userService.changePassword(loginDTO);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get User Profile", notes = "Retrieves the profile of the currently logged-in user")
    @ApiResponse(code = 200, message = "User profile retrieved successfully")
    public UserDTO getMe() {
        return userService.getMe();
    }


    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Login User", notes = "Authenticates a user with given credentials")
    @ApiResponse(code = 200, message = "User logged in successfully")
    public void login(@ApiParam(value = "User login credentials", required = true) @RequestBody LoginResDTO loginResDTO) {
        userService.login(loginResDTO);
    }


    @PutMapping("/activate/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Activate/Deactivate User", notes = "Activates or deactivates a user account")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User activation status changed"),
            @ApiResponse(code = 404, message = "User not found")
    })
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void activate(@ApiParam(value = "ID of the user to activate/deactivate", required = true) @PathVariable Long id) {
        userService.activate(id);
    }

}
