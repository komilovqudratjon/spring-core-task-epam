package com.epam.upskill.springcore.controller;

import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.epam.upskill.springcore.model.dtos.PageGeneral;
import com.epam.upskill.springcore.model.dtos.ResTrainingDTO;
import com.epam.upskill.springcore.model.dtos.TrainingDTO;
import com.epam.upskill.springcore.service.TrainingService;
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

@ContextConfiguration(classes = {TrainingController.class})
@ExtendWith(SpringExtension.class)
class TrainingControllerDiffblueTest {
    @Autowired
    private TrainingController trainingController;

    @MockBean
    private TrainingService trainingService;

    /**
     * Method under test: {@link TrainingController#createOrUpdate(ResTrainingDTO)}
     */
    @Test
    void testCreateOrUpdate() throws Exception {
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.get("/v1/trainings")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new ResTrainingDTO(1L, "janedoe", "janedoe", "Training Name", 1L,
                        Date.from(LocalDate.of(1970, 1, 1).atStartOfDay().atZone(ZoneOffset.UTC).toInstant()), 1)));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(trainingController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(405));
    }

    /**
     * Method under test:
     * {@link TrainingController#getTraineeTrainings(String, Date, Date, String, String, int, int)}
     */
    @Test
    void testGetTraineeTrainings() throws Exception {
        PageGeneral.PageGeneralBuilder<TrainingDTO> builderResult = PageGeneral.builder();
        PageGeneral<TrainingDTO> buildResult = builderResult.content(new ArrayList<>())
                .number(10)
                .size(3)
                .totalElements(1L)
                .build();
        when(trainingService.getTraineeTrainings(Mockito.<String>any(), Mockito.<Date>any(), Mockito.<Date>any(),
                Mockito.<String>any(), Mockito.<String>any(), anyInt(), anyInt())).thenReturn(buildResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/trainings/trainee")
                .param("username", "foo");
        MockMvcBuilders.standaloneSetup(trainingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"content\":[],\"number\":10,\"size\":3,\"totalElements\":1}"));
    }

    /**
     * Method under test:
     * {@link TrainingController#getTrainerTrainings(String, Date, Date, String, String, int, int)}
     */
    @Test
    void testGetTrainerTrainings() throws Exception {
        PageGeneral.PageGeneralBuilder<TrainingDTO> builderResult = PageGeneral.builder();
        PageGeneral<TrainingDTO> buildResult = builderResult.content(new ArrayList<>())
                .number(10)
                .size(3)
                .totalElements(1L)
                .build();
        when(trainingService.getTrainerTrainings(Mockito.<String>any(), Mockito.<Date>any(), Mockito.<Date>any(),
                Mockito.<String>any(), Mockito.<String>any(), anyInt(), anyInt())).thenReturn(buildResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/trainings/trainer")
                .param("username", "foo");
        MockMvcBuilders.standaloneSetup(trainingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content().string("{\"content\":[],\"number\":10,\"size\":3,\"totalElements\":1}"));
    }

    /**
     * Method under test: {@link TrainingController#getTrainingTypes()}
     */
    @Test
    void testGetTrainingTypes() throws Exception {
        when(trainingService.getTrainingTypes()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/trainings/types");
        MockMvcBuilders.standaloneSetup(trainingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link TrainingController#getTrainingTypes()}
     */
    @Test
    void testGetTrainingTypes2() throws Exception {
        when(trainingService.getTrainingTypes()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/trainings/types");
        requestBuilder.characterEncoding("Encoding");
        MockMvcBuilders.standaloneSetup(trainingController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Method under test: {@link TrainingController#createOrUpdate(ResTrainingDTO)}
     */
    @Test
    void testCreateOrUpdate2() throws Exception {
        java.sql.Date trainingDate = mock(java.sql.Date.class);
        when(trainingDate.getTime()).thenReturn(10L);
        ResTrainingDTO resTrainingDTO = new ResTrainingDTO(1L, "janedoe", "janedoe", "Training Name", 1L, trainingDate, 1);

        String content = (new ObjectMapper()).writeValueAsString(resTrainingDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/v1/trainings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(trainingController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(405));
    }
}
