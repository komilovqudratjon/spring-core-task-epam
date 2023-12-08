package com.epam.upskill.springcore.security;

import com.epam.upskill.springcore.model.Users;
import com.epam.upskill.springcore.service.db.common.UserDatabase;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

/**
 * @description: TODO This class is used for CustomUserDetailsService
 * @date: 21 November 2023 $
 * @time: 12:54 AM 06 $
 * @author: Qudratjon Komilov
 */
@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {


    private UserDatabase userDatabase;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userDatabase.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()));
            }

            @Override
            public String getPassword() {
                return user.getPassword();
            }

            @Override
            public String getUsername() {
                return user.getUsername();
            }

            @Override
            public boolean isAccountNonExpired() {
                return user.getIsActive();
            }

            @Override
            public boolean isAccountNonLocked() {
                return user.getIsActive();
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return user.getIsActive();
            }

            @Override
            public boolean isEnabled() {
                return user.getIsActive();
            }
        };
    }
}

