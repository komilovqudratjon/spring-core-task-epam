package com.epam.upskill.springcore.component;

import com.epam.upskill.springcore.model.*;
import com.epam.upskill.springcore.repository.SpecializationRepository;
import com.epam.upskill.springcore.repository.TrainingTypeRepository;
import com.epam.upskill.springcore.repository.UserRepository;
import com.epam.upskill.springcore.service.db.common.TraineeDatabase;
import com.epam.upskill.springcore.service.db.common.TrainerDatabase;
import com.epam.upskill.springcore.service.db.common.TrainingDatabase;
import com.thedeanda.lorem.Lorem;
import com.thedeanda.lorem.LoremIpsum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Date;

/**
 * @description: generate data for database
 * @date: 21 October 2023 $
 * @time: 4:34 PM 55 $
 * @author: Qudratjon Komilov
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final TraineeDatabase traineeRepository;
    private final TrainerDatabase trainerRepository;
    private final TrainingDatabase trainingRepository;
    private final TrainingTypeRepository trainingTypeRepository;
    private final SpecializationRepository specializationRepository;
    private final UserRepository userRepository;
    String password = "password";
    private final PasswordEncoder passwordEncoder;

    public final Lorem lorem = LoremIpsum.getInstance();

    @Override
    public void run(String... args) {
        log.info("Loading data...");

        generateData(10);

        log.info("...data loaded");

    }

    @Transactional
    public void generateData(int count) {
        for (long i = 0; i < count; i++) {
            // Create a new random User
            Users user = new Users();
            user.setDateOfBirth(new Date());
            user.setFirstName(lorem.getName());        // Random first name
            user.setLastName(lorem.getLastName());     // Random last name
            user.setUsername("user" + i);
            user.setRole(RoleName.ROLE_TRAINEE);

            user.setAddress(lorem.getCity() + ", " + lorem.getCountry());// Random address
            // Unique username based on index
            user.setPassword(passwordEncoder.encode(password));   // Random password
            user.setIsActive(true);
            Users user1 = new Users();
            user1.setDateOfBirth(new Date());
            user1.setFirstName(lorem.getName());        // Random first name
            user1.setLastName(lorem.getLastName());     // Random last name
            user1.setUsername("user1" + i);
            user1.setAddress(lorem.getCity() + ", " + lorem.getCountry());// Random address
            user1.setRole(RoleName.ROLE_TRAINER);
            // Unique user1name based on index
            user1.setPassword(passwordEncoder.encode(password));   // Random password
            user1.setIsActive(true);                    // Active user status

            // Save the User to the repository (assuming there is a userRepository)
            try {
                user1 = userRepository.save(user1);
            } catch (Exception ignored) {
            }
            // Save the User to the repository (assuming there is a userRepository)
            try {
                user = userRepository.save(user);
            } catch (Exception ignored) {
            }

            // Now, create a new Trainee with the new User
            Trainee trainee = Trainee.builder()
                    .user(user) // Associate the User we just created
                    .build();

            // Save the Trainee to the repository (assuming there is a traineeRepository)
            try {
                trainee = traineeRepository.save(trainee);
            } catch (Exception ignored) {
            }

            // Now, create a new Specialization
            Specialization specialization = Specialization.builder()
                    .specializationName(lorem.getWords(1, 3)) // Random specializationId name
                    .build();

            // Save the Specialization to the repository (assuming there is a specializationRepository)
            try {
                specialization = specializationRepository.save(specialization);
            } catch (Exception ignored) {
            }

            // Now, create a new Trainer with the new User
            Trainer trainer = Trainer.builder()
                    .user(user1) // Associate the User we just created
                    .specialization(specialization) // Associate the Specialization we just created
                    .build();

            // Save the Trainer to the repository (assuming there is a trainerRepository)
            try {
                trainer = trainerRepository.save(trainer);
            } catch (Exception ignored) {
            }

            // Now, create a new TrainingType
            TrainingType trainingType = TrainingType.builder()
                    .trainingTypeName(lorem.getWords(1, 3)) // Random training type name
                    .build();

            // Save the TrainingType to the repository (assuming there is a trainingTypeRepository)
            try {
                trainingType = trainingTypeRepository.save(trainingType);
            } catch (Exception ignored) {
            }

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
            try {
                training = trainingRepository.save(training);
            } catch (Exception e) {
                log.error("Error saving training: {}", e.getMessage());
            }
        }
        Users user = new Users();
        user.setFirstName("Qudratjon");
        user.setLastName("Komilov");
        user.setUsername("koinot_admin");
        user.setPassword(passwordEncoder.encode("koinot"));
        user.setIsActive(true);
        user.setRole(RoleName.ROLE_ADMIN);

        try {
            user = userRepository.save(user);
        } catch (Exception ignored) {
        }
    }
}
