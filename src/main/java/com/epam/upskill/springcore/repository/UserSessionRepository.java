package com.epam.upskill.springcore.repository;

import com.epam.upskill.springcore.model.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @description: Repository interface for Users entity.
 * @date: 08 November 2023 $
 * @time: 5:37 AM 10 $
 * @author: Qudratjon Komilov
 */
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

    Optional<UserSession> findBySessionId(String sessionId);

    boolean existsBySessionId(String sessionId);
}
