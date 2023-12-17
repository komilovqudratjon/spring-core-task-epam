package com.epam.upskill.springcore.config;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

/**
 * @description: TODO
 * @date: 17 December 2023 $
 * @time: 11:25 PM 25 $
 * @author: Qudratjon Komilov
 */


@Component
public class CustomMetrics {

    private final Counter customCounter;

    public CustomMetrics(MeterRegistry meterRegistry) {
        customCounter = Counter.builder("custom_metric_name")
                .description("Description of custom metric")
                .register(meterRegistry);
    }

    public void incrementCounter() {
        customCounter.increment();
    }
}
