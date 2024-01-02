package com.epam.upskill.springcore.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: TODO to filter all requests and validate the token
 * @date: 29 December 2023 $
 * @time: 8:26 PM 06 $
 * @author: Qudratjon Komilov
 */

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil tokenProvider;
    private final UsersDetails usersDetails;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        try {
            try {
                String bearerToken = request.getHeader("Authorization");
                String jwt = tokenProvider.parseJwt(bearerToken);

                if (StringUtils.hasText(jwt) && tokenProvider.validateAccessToken(jwt)) {
                    String userId = tokenProvider.getUsernameFromJWT(jwt);
                    UserDetails userDetails;

                    userDetails = usersDetails.loadUserByUsername(userId);
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails,
                            null,
                            userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }
            } catch (Exception ex) {
                log.error("Could not set user authentication in security context {}", ex.getMessage());
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            log.error("token error {}", e.getMessage());
        }
    }
}

