package com.epam.upskill.springcore.security;

import com.epam.upskill.springcore.model.UserSession;
import com.epam.upskill.springcore.model.Users;
import com.epam.upskill.springcore.repository.UserSessionRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.UUID;

/**
 * @description: TODO to generate and validate the token
 * @date: 29 December 2023 $
 * @time: 8:32 PM 58 $
 * @author: Qudratjon Komilov
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtUtil {


    @Value("${app.jwtSecret}")
    private String jwtSecret;

    @Value("${app.jwtRefteshSecret}")
    private String jwtRefreshSecret;

    @Value("${app.jwtExpirationInMs}")
    private long jwtExpirationInMs;

    @Value("${app.jwtExpirationInMsForRefreshToken}")
    private long jwtExpirationInMsForRefreshToken;

    private final UserSessionRepository userSessionRepository;

    private String accessToken(Users users, String sessionId) {
        Date expiryDate = new Date(new Date().getTime() + jwtExpirationInMs);
        return Jwts.builder()
                .setSubject(users.getUsername())
                .setId(sessionId)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    private String refreshToken(Users users, String sessionId) {
        Date expiryDate = new Date(new Date().getTime() + jwtExpirationInMsForRefreshToken);
        return Jwts.builder()
                .setSubject(users.getUsername())
                .setId(sessionId)
                .setIssuedAt(new Date())
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtRefreshSecret)
                .compact();
    }

    public UserSession getNewUserSession(Users users) {
        String sessionId = String.valueOf(UUID.randomUUID());
        UserSession userSession = new UserSession();
        userSession.setUser(users);
        userSession.setLastAccessOnline(new Date());
        userSession.setAccessToken(accessToken(users, sessionId));
        userSession.setRefreshToken(refreshToken(users, sessionId));
        userSession.setSessionId(sessionId);
        return userSessionRepository.save(userSession);
    }

    public UserSession getUpdateUserSession(String refreshToken) {
        if (!validateRefreshToken(refreshToken)) {
            throw new RuntimeException("Refresh token is not valid");
        }
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtRefreshSecret).parseClaimsJws(refreshToken);
        String sessionId = claimsJws.getBody().getId();
        UserSession userSession = userSessionRepository.findBySessionId(sessionId).orElseThrow(() -> new RuntimeException("User session not found"));
        if (!userSession.getRefreshToken().equals(refreshToken)) {
            throw new RuntimeException("Refresh token is not valid");
        }
        userSession.setLastAccessOnline(new Date());
        userSession.setAccessToken(accessToken(userSession.getUser(), sessionId));
        userSession.setRefreshToken(refreshToken(userSession.getUser(), sessionId));
        userSession.setSessionId(sessionId);
        return userSessionRepository.save(userSession);
    }

    public boolean deleteSession(String authToken, Users user) {
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(parseJwt(authToken));
        String sessionId = claimsJws.getBody().getId();
        UserSession userSession = userSessionRepository.findBySessionId(sessionId).orElseThrow(() -> new RuntimeException("User session not found"));
        if (!userSession.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("User session not found");
        }
        userSessionRepository.delete(userSession);
        return true;
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public boolean validateAccessToken(String authToken) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            String id = claimsJws.getBody().getId();
            boolean userSession = userSessionRepository.existsBySessionId(id);
            if (!userSession) {
                throw new RuntimeException("User session not found");
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean validateRefreshToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtRefreshSecret).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String parseJwt(String header) {
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }
}

