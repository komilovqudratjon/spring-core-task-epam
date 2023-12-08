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
 * Controller for managing user-related operations in the User Management System.
 * <p>
 * This controller handles various user operations such as changing passwords,
 * retrieving user profiles, login, and activation/deactivation of user accounts.
 * It uses the UserService to perform the actual business logic.
 *
 * @author Qudratjon Komilov
 * @description UserController for User Management System
 * @date 13 November 2023
 * @time 11:25 PM
 */
@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
@Api(tags = "User Management", value = "User Management System")
public class UserController {

    private final UserService userService;


    /**
     * Changes the password for a specific user.
     * <p>
     * This method accepts user login details and uses the UserService to change the password.
     *
     * @param loginDTO the login details including the new password
     * @return LoginResDTO containing the result of the password change operation
     */
    @PostMapping("/change-password")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Change User Password", notes = "Changes the password for a user")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Password changed successfully"), @ApiResponse(code = 400, message = "Invalid user details provided")})
    public LoginResDTO changePassword(@ApiParam(value = "User login information", required = true) @RequestBody LoginDTO loginDTO) {
        return userService.changePassword(loginDTO);
    }

    /**
     * Retrieves the profile of the currently logged-in user.
     * <p>
     * This method calls the UserService to get the details of the current user.
     *
     * @return UserDTO containing the user's profile information
     */
    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get User Profile", notes = "Retrieves the profile of the currently logged-in user")
    @ApiResponse(code = 200, message = "User profile retrieved successfully")
    public UserDTO getMe() {
        return userService.getMe();
    }


    /**
     * Authenticates a user with given credentials.
     * <p>
     * This method takes user login credentials and uses the UserService for authentication.
     *
     * @param loginResDTO the user's login credentials
     */
    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Login User", notes = "Authenticates a user with given credentials")
    @ApiResponse(code = 200, message = "User logged in successfully")
    public void login(@ApiParam(value = "User login credentials", required = true) @RequestBody LoginResDTO loginResDTO) {
        userService.login(loginResDTO);
    }


    /**
     * Activates or deactivates a user account.
     * <p>
     * This method takes a user ID and toggles the activation status of the user's account.
     * It requires admin privileges to perform this operation.
     *
     * @param id the ID of the user to activate or deactivate
     */
    @PutMapping("/activate/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Activate/Deactivate User", notes = "Activates or deactivates a user account")
    @ApiResponses({@ApiResponse(code = 200, message = "User activation status changed"), @ApiResponse(code = 404, message = "User not found")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void activate(@ApiParam(value = "ID of the user to activate/deactivate", required = true) @PathVariable Long id) {
        userService.activate(id);
    }

}
