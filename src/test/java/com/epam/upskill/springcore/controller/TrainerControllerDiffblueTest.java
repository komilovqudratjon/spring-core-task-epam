package com.epam.upskill.springcore.controller;

import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.epam.upskill.springcore.model.dtos.LoginResDTO;
import com.epam.upskill.springcore.model.dtos.PageGeneral;
import com.epam.upskill.springcore.model.dtos.ReqTrainerDTO;
import com.epam.upskill.springcore.model.dtos.RestUserTrainerDTO;
import com.epam.upskill.springcore.model.dtos.SpecializationDTO;
import com.epam.upskill.springcore.model.dtos.TrainerDTO;
import com.epam.upskill.springcore.model.dtos.UserDTO;
import com.epam.upskill.springcore.service.TrainerService;
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
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {TrainerController.class})
@ExtendWith(SpringExtension.class)
class TrainerControllerDiffblueTest {
    @Autowired
    private TrainerController trainerController;

    @MockBean
    private TrainerService trainerService;

    /**
     * Method under test: {@link TrainerController#update(ReqTrainerDTO)}
     */
    @Test
    void testUpdate() throws Exception {
        SpecializationDTO specialization = new SpecializationDTO(1L, "Specialization Name");

        UserDTO user = new UserDTO(1L, "42 Main St", "Jane", "Doe", "janedoe",
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), true);

        when(trainerService.update(Mockito.<ReqTrainerDTO>any()))
                .thenReturn(new TrainerDTO(1L, specialization, user, new ArrayList<>(), true));
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.put("/v1/trainers/update")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new ReqTrainerDTO("janedoe", "Doe", "Jane", "42 Main St",
                        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), true, 1L)));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(trainerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"specialization\":{\"id\":1,\"specializationName\":\"Specialization Name\"},\"user\":{\"id\":1,\"address\":\"42"
                                        + " Main St\",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"username\":\"janedoe\",\"dateOfBirth\":0,\"isActive\":true},"
                                        + "\"trainee\":[],\"isActive\":true}"));
    }

    /**
     * Method under test: {@link TrainerController#register(RestUserTrainerDTO)}
     */
    @Test
    void testRegister() throws Exception {
        when(trainerService.register(Mockito.<RestUserTrainerDTO>any()))
                .thenReturn(new LoginResDTO(1L, "janedoe", "iloveyou"));
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.post("/v1/trainers/register")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new RestUserTrainerDTO("Doe", "Jane", 1L)));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(trainerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"id\":1,\"username\":\"janedoe\",\"password\":\"iloveyou\"}"));
    }

    /**
     * Method under test: {@link TrainerController#getByUsername(String)}
     */
    @Test
    void testGetByUsername() throws Exception {
        SpecializationDTO specialization = new SpecializationDTO(1L, "Specialization Name");

        UserDTO user = new UserDTO(1L, "42 Main St", "Jane", "Doe", "janedoe",
                Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), true);

        when(trainerService.getByUsername(Mockito.<String>any()))
                .thenReturn(new TrainerDTO(1L, specialization, user, new ArrayList<>(), true));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/trainers/username/{username}",
                "janedoe");
        MockMvcBuilders.standaloneSetup(trainerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"specialization\":{\"id\":1,\"specializationName\":\"Specialization Name\"},\"user\":{\"id\":1,\"address\":\"42"
                                        + " Main St\",\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"username\":\"janedoe\",\"dateOfBirth\":0,\"isActive\":true},"
                                        + "\"trainee\":[],\"isActive\":true}"));
    }

    /**
     * Method under test:
     * {@link TrainerController#getNotAssignedTrainers(String, Integer, Integer)}
     */
    @Test
    void testGetNotAssignedTrainers() throws Exception {
        PageGeneral.PageGeneralBuilder<TrainerDTO> builderResult = PageGeneral.builder();
        PageGeneral<TrainerDTO> buildResult = builderResult.content(new ArrayList<>())
                .number(10)
                .size(3)
                .totalElements(1L)
                .build();
        when(trainerService.getNotAssignedTrainers(Mockito.<String>any(), Mockito.<Integer>any(), Mockito.<Integer>any()))
                .thenReturn(buildResult);
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/v1/trainers/not-assigned/{traineeUsername}",
                "janedoe");
        MockHttpServletRequestBuilder paramResult = getResult.param("page", String.valueOf(1));
        MockHttpServletRequestBuilder requestBuilder = paramResult.param("size", String.valueOf(1));
        MockMvcBuilders.standaloneSetup(trainerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"content\":[],\"number\":10,\"size\":3,\"totalElements\":1}"));
    }

    /**
     * Method under test: {@link TrainerController#activate(String, boolean)}
     */
    @Test
    void testActivate() throws Exception {
        doNothing().when(trainerService).activate(Mockito.<String>any(), anyBoolean());
        MockHttpServletRequestBuilder patchResult = MockMvcRequestBuilders.patch("/v1/trainers/status");
        MockHttpServletRequestBuilder requestBuilder = patchResult.param("isActive", String.valueOf(true))
                .param("username", "foo");
        MockMvcBuilders.standaloneSetup(trainerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
