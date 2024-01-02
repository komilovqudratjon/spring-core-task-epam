package com.epam.upskill.springcore.config.loging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @date: 08 December 2023 $
 * @time: 1:23 PM 57 $
 * @author: Qudratjon Komilov
 */
@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - start;

            logger.info(joinPoint.getSignature() + " executed in " + executionTime + "ms");
            return result;
        } catch (Exception e) {
            logger.error("Exception in " + joinPoint.getSignature(), e);
            throw e;
        }
    }
}

