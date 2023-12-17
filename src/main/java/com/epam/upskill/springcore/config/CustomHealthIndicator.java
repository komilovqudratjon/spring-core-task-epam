package com.epam.upskill.springcore.config;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * @description: TODO
 * @date: 17 December 2023 $
 * @time: 11:24 PM 55 $
 * @author: Qudratjon Komilov
 */


@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        int errorCode = check(); // perform some specific health check
        if (errorCode != 0) {
            return Health.down().withDetail("Error Code", errorCode).build();
        }
        return Health.up().build();
    }

    public int check() {
        // Your logic to check health
        return 0; // return non-zero in case of an error
    }
}
