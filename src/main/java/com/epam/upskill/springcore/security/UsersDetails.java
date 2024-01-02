package com.epam.upskill.springcore.security;

import com.epam.upskill.springcore.service.db.common.UserDatabase;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * @description: TODO This class is used for UsersDetails
 * @date: 21 November 2023 $
 * @time: 12:54 AM 06 $
 * @author: Qudratjon Komilov
 */
@Component
@AllArgsConstructor
public class UsersDetails implements UserDetailsService {


    private final UserDatabase userDatabase;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDatabase.findByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found with username: " + username));
    }
}

