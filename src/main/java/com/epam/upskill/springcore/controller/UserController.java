package com.epam.upskill.springcore.controller;

import com.epam.upskill.springcore.model.dtos.LoginDTO;
import com.epam.upskill.springcore.model.dtos.RestUserDTO;
import com.epam.upskill.springcore.model.dtos.UserDTO;
import com.epam.upskill.springcore.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;


/**
 * @description: TODO
 * @date: 13 November 2023 $
 * @time: 11:25 PM 24 $
 * @author: Qudratjon Komilov
 */
@RestController
@RequestMapping("/v1/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Endpoint for creating or updating a user.
     *
     * @param restUserDTO The user data transfer object.
     * @return ResponseEntity containing the created or updated user.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO register(@RequestBody RestUserDTO restUserDTO) {
        return userService.register(restUserDTO);
    }

    /**
     * user password change.
     * @param loginDTO The user data transfer object.
     * @return ResponseEntity containing the created or updated user.
     */
    @PostMapping("/change-password")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDTO changePassword(@RequestBody LoginDTO loginDTO) {
        return userService.changePassword(loginDTO);
    }

    /**
     * Update user profile
     * @param restUserDTO The user data transfer object.
     */
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO update(@RequestBody RestUserDTO restUserDTO) {
        return userService.update(restUserDTO);
    }


    /**
     * Activate/De-activate user
     * @param id The user id.
     */
    @PutMapping("/activate/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void activate(@PathVariable Long id) {
        userService.activate(id);
    }

}
