package com.epam.upskill.springcore.config.loging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @description: TODO
 * @date: 08 December 2023 $
 * @time: 1:24 PM 28 $
 * @author: Qudratjon Komilov
 */
@Component
public class TransactionLoggingInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String transactionId = UUID.randomUUID().toString();
        MDC.put("transactionId", transactionId);
        logger.info("Transaction Start - {} {} - Transaction ID: {}", request.getMethod(), request.getRequestURI(), transactionId);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // This can be used if you want to log more details after the handler method is invoked
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        logger.info("Transaction End - {} {} - Status: {} - Transaction ID: {}", request.getMethod(), request.getRequestURI(), response.getStatus(), MDC.get("transactionId"));
        MDC.clear();
    }
}
