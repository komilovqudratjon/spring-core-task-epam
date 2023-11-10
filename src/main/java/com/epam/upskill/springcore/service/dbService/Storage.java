package com.epam.upskill.springcore.service.dbService;

import com.epam.upskill.springcore.model.Trainee;
import com.epam.upskill.springcore.model.Trainer;
import com.epam.upskill.springcore.model.Training;
import com.epam.upskill.springcore.service.dbService.DAO.TraineeDAO;
import com.epam.upskill.springcore.service.dbService.DAO.TrainerDAO;
import com.epam.upskill.springcore.service.dbService.DAO.TrainingDAO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @className: Storage  $
 * @description: TODO
 * @date: 09 November 2023 $
 * @time: 5:31 PM 28 $
 * @author: Qudratjon Komilov
 */


@Component
@Slf4j
public class Storage {

    @Value("${storage.init.filepath.trainers}")
    private String trainersFilePath;

    @Value("${storage.init.filepath.trainees}")
    private String traineesFilePath;

    @Value("${storage.init.filepath.trainings}")
    private String trainingsFilePath;

    private final TraineeDAO traineeDAO;
    private final TrainerDAO trainerDAO;
    private final TrainingDAO trainingDAO;

    private final Gson gson = new Gson();


    public Storage(TraineeDAO traineeDAO, TrainerDAO trainerDAO, TrainingDAO trainingDAO) {
        this.traineeDAO = traineeDAO;
        this.trainerDAO = trainerDAO;
        this.trainingDAO = trainingDAO;
    }



    @PostConstruct
    public void generateData() {

    }

    @PostConstruct
    public void init() {
        try {
            loadTrainers();
        } catch (IOException e) {
            log.error("Error loading trainers {}", e.getMessage());
        }
        try {
            loadTrainees();
        } catch (IOException e) {
            log.error("Error loading trainees {}", e.getMessage());
        }
        try {
            loadTrainings();
        } catch (IOException e) {
            log.error("Error loading trainings {}", e.getMessage());
        }
    }

    @PreDestroy
    public void onDestroy() throws IOException {
        saveTraineeToJSON();
        saveTrainerToJSON();
        saveTrainingToJSON();
    }

    private void loadTrainers() throws IOException {
        String trainersJson = new String(Files.readAllBytes(Paths.get(trainersFilePath)));
        Type trainerListType = new TypeToken<List<Trainer>>() {}.getType();
        List<Trainer> trainerList = gson.fromJson(trainersJson, trainerListType);
        if (trainerList != null) {
            trainerList.forEach(trainerDAO::save);
        }
    }

    private void loadTrainees() throws IOException {
        String traineesJson = new String(Files.readAllBytes(Paths.get(traineesFilePath)));
        Type traineeListType = new TypeToken<List<Trainee>>() {}.getType();
        List<Trainee> traineeList = gson.fromJson(traineesJson, traineeListType);
        if (traineeList != null){
            traineeList.forEach(traineeDAO::save);
        }
    }

    private void loadTrainings() throws IOException {
        String trainingsJson = new String(Files.readAllBytes(Paths.get(trainingsFilePath)));
        Type trainingListType = new TypeToken<List<Training>>() {}.getType();
        List<Training> trainingList = gson.fromJson(trainingsJson, trainingListType);
        if (trainingList != null){
            trainingList.forEach(trainingDAO::save);
        }
    }

    public void saveTraineeToJSON() throws IOException {
        String traineesJson = gson.toJson(traineeDAO.findAll());
        Files.write(Paths.get(traineesFilePath), traineesJson.getBytes());
    }

    public void saveTrainerToJSON() throws IOException {
        String trainersJson = gson.toJson(trainerDAO.findAll());
        Files.write(Paths.get(trainersFilePath), trainersJson.getBytes());
    }

    public void saveTrainingToJSON() throws IOException {
        String trainingsJson = gson.toJson(trainingDAO.findAll());
        Files.write(Paths.get(trainingsFilePath), trainingsJson.getBytes());
    }

}

