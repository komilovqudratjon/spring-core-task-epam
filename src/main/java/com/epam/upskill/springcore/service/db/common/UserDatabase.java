package com.epam.upskill.springcore.service.db.common;

import com.epam.upskill.springcore.model.Users;
import com.epam.upskill.springcore.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
@Slf4j
public class UserDatabase {

    private final UserRepository userRepository;

    /**
     * Saves a Users entity.
     * The method first saves the entity using the JPA repository,
     * then saves the same entity in the DAO, and logs the process.
     *
     * @param entity The Users entity to be saved.
     * @return The saved Users entity.
     */
    public Users save(Users entity) {
        log.debug("Saving a Users entity");
        Users savedEntity = userRepository.save(entity);
        log.info("Users entity saved with ID: {}", savedEntity.getId());
        return savedEntity;
    }

    /**
     * Finds a Users entity by its ID.
     * It first attempts to find the entity in the DAO;
     * if not found, it searches the JPA repository.
     * If found in the repository, it is then saved back to the DAO.
     *
     * @param id The ID of the Users entity to find.
     * @return An Optional containing the found Users entity or empty if not found.
     */
    public Optional<Users> findById(Long id) {
        log.trace("Attempting to find Users by ID: {}", id);

        return userRepository.findById(id);

    }

    /**
     * Deletes a Users entity by its ID.
     * The entity is deleted from both the DAO and the JPA repository.
     *
     * @param id The ID of the Users entity to be deleted.
     */
    public void deleteById(Long id) {
        log.info("Deleting Users entity with ID: {}", id);
        userRepository.deleteById(id);
    }

    /**
     * Finds all Users entities.
     * Initially fetches entities from the DAO; if none found, it fetches from the repository.
     * All fetched entities are saved back to the DAO.
     *
     * @return A list of all Users entities.
     */
    public List<Users> findAll() {
        log.debug("Fetching all Users entities");
        return userRepository.findAll();

    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public Optional<Users> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
