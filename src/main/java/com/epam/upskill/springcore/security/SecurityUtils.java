package com.epam.upskill.springcore.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @description: TODO
 * @date: 21 November 2023 $
 * @time: 4:03 PM 35 $
 * @author: Qudratjon Komilov
 */


public class SecurityUtils {

    public static String getCurrentUserUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}

