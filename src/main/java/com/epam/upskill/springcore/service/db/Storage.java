package com.epam.upskill.springcore.service.db;

import com.epam.upskill.springcore.model.Trainee;
import com.epam.upskill.springcore.model.Trainer;
import com.epam.upskill.springcore.model.Training;
import com.epam.upskill.springcore.service.db.dao.TraineeDAO;
import com.epam.upskill.springcore.service.db.dao.TrainerDAO;
import com.epam.upskill.springcore.service.db.dao.TrainingDAO;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * @description: Storage class for managing the persistence of Trainees, Trainers, and Trainings.
 * It handles loading from and saving to JSON files.
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

    /**
     * Initializes the storage by loading data from JSON files.
     */
    @PostConstruct
    public void init() {
        log.info("Initializing Storage");
        creteFile(trainersFilePath);
        creteFile(traineesFilePath);
        creteFile(trainingsFilePath);

        try {
            loadTrainers();
            log.info("Trainers loaded successfully.");
        } catch (IOException e) {
            log.error("Error loading trainers: {}", e.getMessage(), e);
        }
        try {
            loadTrainees();
            log.info("Trainees loaded successfully.");
        } catch (IOException e) {
            log.error("Error loading trainees: {}", e.getMessage(), e);
        }
        try {
            loadTrainings();
            log.info("Trainings loaded successfully.");
        } catch (IOException e) {
            log.error("Error loading trainings: {}", e.getMessage(), e);
        }
    }

    /**
     * Creates a file if it doesn't exist.
     * @param filePath the path of the file to be created
     */
    public void creteFile(String filePath) {
        if (Files.notExists(Paths.get(filePath))) {
            // crete file
            try {
                Files.createFile(Paths.get(filePath));
            } catch (IOException e) {
                log.error("Error creating file: {}", e.getMessage(), e);
            }
        }
    }

    /**
     * Saves all data to JSON files before the destruction of the bean.
     */
    @PreDestroy
    public void onDestroy() {
        log.info("Saving data to JSON before shutdown");
        try {
            saveTraineeToJSON();
            log.info("Trainees saved to JSON.");
        } catch (IOException e) {
            log.error("Error saving trainees: {}", e.getMessage(), e);
        }
        try {
            saveTrainerToJSON();
            log.info("Trainers saved to JSON.");
        } catch (IOException e) {
            log.error("Error saving trainers: {}", e.getMessage(), e);
        }
        try {
            saveTrainingToJSON();
            log.info("Trainings saved to JSON.");
        } catch (IOException e) {
            log.error("Error saving trainings: {}", e.getMessage(), e);
        }
    }

    /**
     * Loads trainers from the JSON file.
     *
     * @throws IOException if file reading fails
     */
    private void loadTrainers() throws IOException {
        log.trace("Loading trainers from JSON");
        String trainersJson = new String(Files.readAllBytes(Paths.get(trainersFilePath)));
        Type trainerListType = new TypeToken<List<Trainer>>() {
        }.getType();
        List<Trainer> trainerList = gson.fromJson(trainersJson, trainerListType);
        if (trainerList != null) {
            trainerList.forEach(trainerDAO::save);
        }
    }

    /**
     * Loads trainees from the JSON file.
     *
     * @throws IOException if file reading fails
     */
    private void loadTrainees() throws IOException {
        log.trace("Loading trainees from JSON");
        String traineesJson = new String(Files.readAllBytes(Paths.get(traineesFilePath)));
        Type traineeListType = new TypeToken<List<Trainee>>() {
        }.getType();
        List<Trainee> traineeList = gson.fromJson(traineesJson, traineeListType);
        if (traineeList != null) {
            traineeList.forEach(traineeDAO::save);
        }
    }

    /**
     * Loads trainings from the JSON file.
     *
     * @throws IOException if file reading fails
     */
    private void loadTrainings() throws IOException {
        log.trace("Loading trainings from JSON");
        String trainingsJson = new String(Files.readAllBytes(Paths.get(trainingsFilePath)));
        Type trainingListType = new TypeToken<List<Training>>() {
        }.getType();
        List<Training> trainingList = gson.fromJson(trainingsJson, trainingListType);
        if (trainingList != null) {
            trainingList.forEach(trainingDAO::save);
        }
    }

    /**
     * Saves trainees to the JSON file.
     *
     * @throws IOException if file writing fails
     */
    private void saveTraineeToJSON() throws IOException {
        log.trace("Saving trainees to JSON");
        String traineesJson = gson.toJson(traineeDAO.findAll());
        Files.write(Paths.get(traineesFilePath), traineesJson.getBytes());
    }

    /**
     * Saves trainers to the JSON file.
     *
     * @throws IOException if file writing fails
     */
    private void saveTrainerToJSON() throws IOException {
        log.trace("Saving trainers to JSON");
        String trainersJson = gson.toJson(trainerDAO.findAll());
        Files.write(Paths.get(trainersFilePath), trainersJson.getBytes());
    }

    /**
     * Saves trainings to the JSON file.
     *
     * @throws IOException if file writing fails
     */
    private void saveTrainingToJSON() throws IOException {
        log.trace("Saving trainings to JSON");
        String trainingsJson = gson.toJson(trainingDAO.findAll());
        Files.write(Paths.get(trainingsFilePath), trainingsJson.getBytes());
    }
}
