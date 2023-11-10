package com.epam.upskill.springcore.component;

import com.epam.upskill.springcore.model.*;
import com.epam.upskill.springcore.repository.*;
import com.epam.upskill.springcore.service.dbService.common.TraineeDB;
import com.epam.upskill.springcore.service.dbService.common.TrainerDB;
import com.epam.upskill.springcore.service.dbService.common.TrainingDB;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @className: DataLoader  $
 * @description: TODO
 * @date: 21 October 2023 $
 * @time: 4:34 PM 55 $
 * @author: Qudratjon Komilov
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final TraineeDB traineeRepository;
    private final TrainerDB trainerRepository;
    private final TrainingDB trainingRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private final SpecializationRepository specializationRepository;
    private final UserRepository userRepository;

    public final Lorem lorem = LoremIpsum.getInstance();

    @Override
    public void run(String... args) {
        log.info("Loading data...");

//        generateData();

        log.info("...data loaded");

    }

    private void generateData() {
        for (long i = 0; i < 100; i++) {
            // Create a new random User
            Users user = new Users();
            user.setFirstName(lorem.getName());        // Random first name
            user.setLastName(lorem.getLastName());     // Random last name
            user.setUsername("user" + i);              // Unique username based on index
            user.setPassword(lorem.getWords(1, 10));   // Random password
            user.setIsActive(true);                    // Active user status

            // Save the User to the repository (assuming there is a userRepository)
            user = userRepository.save(user);

            // Now, create a new Trainee with the new User
            Trainee trainee = Trainee.builder()
                    .dateOfBirth(new Date()) // Replace with a randomly generated Date
                    .address(lorem.getCity() + ", " + lorem.getCountry()) // Random address
                    .user(user) // Associate the User we just created
                    .build();

            // Save the Trainee to the repository (assuming there is a traineeRepository)
            traineeRepository.save(trainee);

            // Now, create a new Specialization
            Specialization specialization = Specialization.builder()
                    .specializationName(lorem.getWords(1, 3)) // Random specialization name
                    .build();

            // Save the Specialization to the repository (assuming there is a specializationRepository)
            specializationRepository.save(specialization);

            // Now, create a new Trainer with the new User
            Trainer trainer = Trainer.builder()
                    .user(user) // Associate the User we just created
                    .specialization(specialization) // Associate the Specialization we just created
                    .build();

            // Save the Trainer to the repository (assuming there is a trainerRepository)
            trainerRepository.save(trainer);

            // Now, create a new TrainingType
            TrainingType trainingType = TrainingType.builder()
                    .trainingTypeName(lorem.getWords(1, 3)) // Random training type name
                    .build();

            // Save the TrainingType to the repository (assuming there is a trainingTypeRepository)
            trainingTypeRepository.save(trainingType);

            // Now, create a new Training
            Training training = Training.builder()
                    .trainee(trainee) // Associate the Trainee we just created
                    .trainer(trainer) // Associate the Trainer we just created
                    .trainingName(lorem.getWords(1, 3)) // Random training name
                    .trainingType(trainingType) // Associate the TrainingType we just created
                    .trainingDate(new Date()) // Replace with a randomly generated Date
                    .trainingDuration(1) // Random training duration
                    .build();

            // Save the Training to the repository (assuming there is a trainingRepository)
            trainingRepository.save(training);
        }
    }
}