package com.epam.upskill.springcore.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.epam.upskill.springcore.model.dtos.LoginDTO;
import com.epam.upskill.springcore.model.dtos.LoginResDTO;
import com.epam.upskill.springcore.model.dtos.UserDTO;
import com.epam.upskill.springcore.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
class UserControllerDiffblueTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    /**
     * Method under test: {@link UserController#changePassword(LoginDTO)}
     */
    @Test
    void testChangePassword() throws Exception {
        when(userService.changePassword(Mockito.<LoginDTO>any())).thenReturn(new LoginResDTO(1L, "janedoe", "iloveyou"));
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/v1/users/change-password")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new LoginDTO(1L, "janedoe", "iloveyou", "iloveyou")));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"id\":1,\"username\":\"janedoe\",\"password\":\"iloveyou\"}"));
    }

    /**
     * Method under test: {@link UserController#getMe()}
     */
    @Test
    void testGetMe() throws Exception {
        when(userService.getMe()).thenReturn(new UserDTO(1L, "42 Main St", "Jane", "Doe", "janedoe",
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), true));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/users/me");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"address\":\"42 Main St\",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"username\":\"janedoe\",\"dateOfBirth"
                                        + "\":0,\"isActive\":true}"));
    }

    /**
     * Method under test: {@link UserController#login(LoginResDTO)}
     */
    @Test
    void testLogin() throws Exception {
        doNothing().when(userService).login(Mockito.<LoginResDTO>any());
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/v1/users/login")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new LoginResDTO(1L, "janedoe", "iloveyou")));
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link UserController#activate(Long)}
     */
    @Test
    void testActivate() throws Exception {
        doNothing().when(userService).activate(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/users/activate/{id}", 1L);
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link UserController#activate(Long)}
     */
    @Test
    void testActivate2() throws Exception {
        doNothing().when(userService).activate(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/v1/users/activate/{id}", 1L);
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
