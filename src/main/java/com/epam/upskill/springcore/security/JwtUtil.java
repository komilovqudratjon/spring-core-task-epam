package com.epam.upskill.springcore.security;

import com.epam.upskill.springcore.model.UserSession;
import com.epam.upskill.springcore.model.Users;
import com.epam.upskill.springcore.model.dtos.JwtResponse;
import com.epam.upskill.springcore.repository.UserSessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {

    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtRefreshSecret}")
    private String jwtRefreshSecret;

    @Value("${app.jwtExpirationInMs}")
    private long jwtExpirationInMs;

    @Value("${app.jwtExpirationInMsForRefreshToken}")
    private long jwtExpirationInMsForRefreshToken;

    private final UserSessionRepository userSessionRepository;

    public JwtResponse getNewUserSession(Users user) {
        String sessionId = UUID.randomUUID().toString();
        return createUserSession(user, sessionId);
    }

    public JwtResponse getUpdatedUserSession(String refreshToken) {
        validateToken(refreshToken, jwtRefreshSecret);
        String sessionId = getSessionIdFromToken(refreshToken, jwtRefreshSecret);
        return updateUserSession(sessionId, refreshToken);
    }

    public boolean deleteSession(String authToken, Users user) {
        String sessionId = getSessionIdFromToken(authToken, jwtSecret);
        return deleteUserSession(sessionId, user);
    }

    public String getUsernameFromJWT(String token) {
        return parseClaims(token, jwtSecret).getSubject();
    }

    public boolean validateAccessToken(String authToken) {
        return validateToken(authToken, jwtSecret);
    }

    public boolean validateRefreshToken(String authToken) {
        return validateToken(authToken, jwtRefreshSecret);
    }

    private String generateToken(Users user, String sessionId, long expirationTimeMs, String secret) {
        Date expiryDate = new Date(new Date().getTime() + expirationTimeMs);
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setId(sessionId)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private JwtResponse createUserSession(Users user, String sessionId) {
        UserSession userSession = new UserSession();
        userSession.setUser(user);
        userSession.setLastAccessOnline(new Date());
        userSession.setAccessToken(generateToken(user, sessionId, jwtExpirationInMs, jwtSecret));
        userSession.setRefreshToken(generateToken(user, sessionId, jwtExpirationInMsForRefreshToken, jwtRefreshSecret));
        userSession.setSessionId(sessionId);
        return buildJwtResponse(userSessionRepository.save(userSession), user);
    }

    private JwtResponse updateUserSession(String sessionId, String refreshToken) {
        UserSession userSession = findUserSession(sessionId);
        validateToken(refreshToken, jwtRefreshSecret, userSession.getRefreshToken());
        userSession.setLastAccessOnline(new Date());
        userSession.setAccessToken(generateToken(userSession.getUser(), sessionId, jwtExpirationInMs, jwtSecret));
        userSession.setRefreshToken(generateToken(userSession.getUser(), sessionId, jwtExpirationInMsForRefreshToken, jwtRefreshSecret));
        return buildJwtResponse(userSessionRepository.save(userSession), userSession.getUser());
    }

    private boolean deleteUserSession(String sessionId, Users user) {
        UserSession userSession = findUserSession(sessionId);
        validateUserSession(userSession, user);
        userSessionRepository.delete(userSession);
        return true;
    }

    private UserSession findUserSession(String sessionId) {
        return userSessionRepository.findBySessionId(sessionId)
                .orElseThrow(() -> new RuntimeException("User session not found"));
    }

    private void validateUserSession(UserSession userSession, Users user) {
        if (!userSession.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("User session not found for the given user");
        }
    }

    private String getSessionIdFromToken(String token, String secret) {
        return parseClaims(token, secret).getId();
    }

    private Claims parseClaims(String token, String secret) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private boolean validateToken(String authToken, String secret) {
        try {
            parseClaims(authToken, secret);
            return true;
        } catch (Exception e) {
            log.error("Invalid token", e);
            return false;
        }
    }

    private void validateToken(String authToken, String secret, String expectedToken) {
        if (!authToken.equals(expectedToken)) {
            throw new RuntimeException("Token validation failed");
        }
    }

    public String parseJwt(String header) {
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    private JwtResponse buildJwtResponse(UserSession userSession, Users users) {
        return JwtResponse.builder()
                .accessToken(userSession.getAccessToken())
                .refreshToken(userSession.getRefreshToken())
                .sessionId(userSession.getSessionId())
                .username(users.getUsername())
                .lastName(users.getLastName())
                .firstName(users.getFirstName())
                .role(users.getRole())
                .build();
    }
}
