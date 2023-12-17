package com.epam.upskill.springcore.controller;

import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.epam.upskill.springcore.model.dtos.LoginResDTO;
import com.epam.upskill.springcore.model.dtos.ReqTraineeDTO;
import com.epam.upskill.springcore.model.dtos.ReqUserTraineeDTO;
import com.epam.upskill.springcore.model.dtos.TraineeDTO;
import com.epam.upskill.springcore.model.dtos.UserDTO;
import com.epam.upskill.springcore.service.TraineeService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {TraineeController.class})
@ExtendWith(SpringExtension.class)
class TraineeControllerDiffblueTest {
    @Autowired
    private TraineeController traineeController;

    @MockBean
    private TraineeService traineeService;

    /**
     * Method under test: {@link TraineeController#register(ReqUserTraineeDTO)}
     */
    @Test
    void testRegister() throws Exception {
        when(traineeService.register(Mockito.<ReqUserTraineeDTO>any()))
                .thenReturn(new LoginResDTO(1L, "janedoe", "iloveyou"));
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/v1/trainees/register")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new ReqUserTraineeDTO("Jane", "Doe",
                        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), "42 Main St")));
        MockMvcBuilders.standaloneSetup(traineeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"id\":1,\"username\":\"janedoe\",\"password\":\"iloveyou\"}"));
    }

    /**
     * Method under test: {@link TraineeController#deleteByUsername(String)}
     */
    @Test
    void testDeleteByUsername() throws Exception {
        doNothing().when(traineeService).deleteByUsername(Mockito.<String>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/v1/trainees/username/{username}",
                "janedoe");
        MockMvcBuilders.standaloneSetup(traineeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link TraineeController#deleteByUsername(String)}
     */
    @Test
    void testDeleteByUsername2() throws Exception {
        doNothing().when(traineeService).deleteByUsername(Mockito.<String>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/v1/trainees/username/{username}",
                "janedoe");
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(traineeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link TraineeController#getByUsername(String)}
     */
    @Test
    void testGetByUsername() throws Exception {
        UserDTO user = new UserDTO(1L, "42 Main St", "Jane", "Doe", "janedoe",
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), true);

        when(traineeService.getByUsername(Mockito.<String>any()))
                .thenReturn(new TraineeDTO(1L, user, new ArrayList<>(), true));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/trainees/username/{username}",
                "janedoe");
        MockMvcBuilders.standaloneSetup(traineeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"user\":{\"id\":1,\"address\":\"42 Main St\",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"username\":\"janedoe"
                                        + "\",\"dateOfBirth\":0,\"isActive\":true},\"trainers\":[],\"isActive\":true}"));
    }

    /**
     * Method under test: {@link TraineeController#setActiveStatus(String, boolean)}
     */
    @Test
    void testSetActiveStatus() throws Exception {
        doNothing().when(traineeService).setActiveStatus(Mockito.<String>any(), anyBoolean());
        MockHttpServletRequestBuilder patchResult = MockMvcRequestBuilders.patch("/v1/trainees/active-status");
        MockHttpServletRequestBuilder requestBuilder = patchResult.param("isActive", String.valueOf(true))
                .param("username", "foo");
        MockMvcBuilders.standaloneSetup(traineeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    /**
     * Method under test: {@link TraineeController#updateTrainee(ReqTraineeDTO)}
     */
    @Test
    void testUpdateTrainee() throws Exception {
        UserDTO user = new UserDTO(1L, "42 Main St", "Jane", "Doe", "janedoe",
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), true);

        when(traineeService.update(Mockito.<ReqTraineeDTO>any()))
                .thenReturn(new TraineeDTO(1L, user, new ArrayList<>(), true));
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.put("/v1/trainees/update")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult.content(objectMapper.writeValueAsString(
                new ReqTraineeDTO(Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()),
                        "janedoe", "Doe", "Jane", true, "42 Main St")));
        MockMvcBuilders.standaloneSetup(traineeController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"user\":{\"id\":1,\"address\":\"42 Main St\",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"username\":\"janedoe"
                                        + "\",\"dateOfBirth\":0,\"isActive\":true},\"trainers\":[],\"isActive\":true}"));
    }
}
