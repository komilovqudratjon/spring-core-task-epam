package com.epam.upskill.springcore.config.loging;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Component
@Slf4j
public class TransactionLoggingInterceptor implements HandlerInterceptor {

    // Constants for repeated string literals
    private static final String TRANSACTION_ID = "transactionId";
    private static final String START_TIME = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String transactionId = UUID.randomUUID().toString();
        MDC.put(TRANSACTION_ID, transactionId);

        log.info("Transaction Start - [{} {}] - Transaction ID: {}", request.getMethod(), request.getRequestURI(), transactionId);

        // Store start time for calculating execution time
        request.setAttribute(START_TIME, System.currentTimeMillis());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        String transactionId = MDC.get(TRANSACTION_ID);

        Long startTime = (Long) request.getAttribute(START_TIME);
        Long endTime = System.currentTimeMillis();
        Long executeTime = (startTime != null) ? endTime - startTime : null;

        log.info("Transaction Executed - [{} {}] - Execution Time: {} ms - Transaction ID: {}",
                request.getMethod(), request.getRequestURI(), executeTime, transactionId);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        log.info("Transaction End - [{} {}] - Status: {} - Transaction ID: {}", request.getMethod(), request.getRequestURI(), response.getStatus(), MDC.get(TRANSACTION_ID));
        MDC.clear();
    }
}
