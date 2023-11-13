package com.epam.upskill.springcore.service.impl;

import com.epam.upskill.springcore.model.dtos.ResTraineeDTO;
import com.epam.upskill.springcore.model.dtos.TraineeDTO;
import com.epam.upskill.springcore.model.Trainee;
import com.epam.upskill.springcore.repository.UserRepository;
import com.epam.upskill.springcore.service.TraineeService;
import com.epam.upskill.springcore.service.db.specifications.TraineeSpecifications;
import com.epam.upskill.springcore.service.db.common.TraineeDatabase;
import com.epam.upskill.springcore.service.impl.mapper.TraineeDTOMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.Optional;

/**
 * @description: Service class for managing Trainee entities.
 * @date: 08 November 2023 $
 * @time: 5:43 AM 02 $
 * @author: Qudratjon Komilov
 */


@Service
@AllArgsConstructor
@Slf4j
public class TraineeServiceImpl implements TraineeService {

    private final TraineeDatabase traineeRepository;

    private final UserRepository userRepository;

    private final TraineeDTOMapper traineeDTOMapper;

    /**
     * Creates or updates a Trainee.
     *
     * @param trainee the trainee to create or update
     * @return the created or updated TraineeDTO
     */
    @Override
    public TraineeDTO createOrUpdate(ResTraineeDTO trainee) {

        log.debug("Creating or updating trainee: {}", trainee);
        Trainee trainee1 = new Trainee();
        trainee1.setId(trainee.id());
        trainee1.setDateOfBirth(trainee.dateOfBirth());
        trainee1.setAddress(trainee.address());
        trainee1.setUser(userRepository.findById(trainee.userId()).orElseThrow(() -> {
            log.error("User not found by id: {}", trainee.userId());
            return new EntityNotFoundException("User not found by id: " + trainee.userId());
        }));
        TraineeDTO apply = traineeDTOMapper.apply(traineeRepository.save(trainee1));
        log.debug("Created or updated trainee: {}", apply);
        return apply;
    }

    /**
     * Deletes a Trainee by its ID.
     *
     * @param id the ID of the trainee
     */
    @Override
    public void delete(Long id) {
        log.debug("Deleting trainee by id: {}", id);
        traineeRepository.deleteById(id);
        log.debug("Deleted trainee by id: {}", id);
    }

    /**
     * Retrieves a Trainee by its ID.
     *
     * @param id the ID of the trainee
     * @return the TraineeDTO
     * @throws EntityNotFoundException if the trainee is not found
     */
    @Override
    public TraineeDTO getById(Long id) {
        log.debug("Retrieving trainee by id: {}", id);
        Optional<Trainee> byId = traineeRepository.findById(id);
        log.debug("Retrieved trainee by id: {}", byId);
        return byId.map(traineeDTOMapper).orElseThrow(() -> {
            log.error("Trainee not found by id: {}", id);
            return new EntityNotFoundException("Trainee not found by id: " + id);
        });
    }

    /**
     * Retrieves all Trainees by filter
     *
     * @return a list of TraineeDTOs
     */
    @Override
    public Page<TraineeDTO> getByFilter(Pageable pageable, Date dateOfBirth, String address) {
        log.debug("Retrieving all trainees");
        Page<Trainee> trainees = traineeRepository.findAll(new TraineeSpecifications(dateOfBirth, address), pageable);
        log.debug("Retrieved all trainees: {}", trainees);
        return trainees.map(traineeDTOMapper);
    }

}

