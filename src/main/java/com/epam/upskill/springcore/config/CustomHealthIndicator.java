package com.epam.upskill.springcore.config;

import lombok.AllArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @description: TODO
 * @date: 17 December 2023 $
 * @time: 11:24 PM 55 $
 * @author: Qudratjon Komilov
 */


@Component
@AllArgsConstructor
public class CustomHealthIndicator implements HealthIndicator {

    private final DataSource dataSource;

    @Override
    public Health health() {
        int errorCode = check(); // perform some specific health check
        if (errorCode != 0) {
            return Health.down().withDetail("Error Code", errorCode).build();
        }
        return Health.up().build();
    }

    public int check() {
        try (Connection connection = dataSource.getConnection()) {
            return connection.isValid(10) ? 0 : 1;
        } catch (Exception e) {
            return 1; // DataSource is not available or other errors
        }
    }
}
