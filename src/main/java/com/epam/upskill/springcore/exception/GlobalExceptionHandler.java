package com.epam.upskill.springcore.exception;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.TransientPropertyValueException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import java.util.List;

/**
 * @description: Global exception handler for the application.
 * @date: 12 November 2023 $
 * @time: 2:05 PM 21 $
 * @author: Qudratjon Komilov
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    /**
     * Handles EntityNotFoundException.
     *
     * @param e the caught EntityNotFoundException.
     * @return a ResponseEntity with error details and HTTP status code.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException e) {
        log.error("EntityNotFoundException: ", e);
        ErrorResponse errorResponse = new ErrorResponse("Entity not found: " + e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
    }

    /**
     * Handles generic Exceptions.
     *
     * @param e the caught Exception.
     * @return a ResponseEntity with error details and HTTP status code.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception e) {
        log.error("General Exception: ", e);
        ErrorResponse errorResponse = new ErrorResponse("Internal server error: " + e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    /**
     * Handles exceptions related to JPA, Data Integrity, Invalid Data Access API Usage, and Transient Property Value.
     * Consolidated for simplicity as they all result in BAD_REQUEST.
     *
     * @param e the caught RuntimeException.
     * @return a ResponseEntity with error details and HTTP status code.
     */
    @ExceptionHandler(TransientPropertyValueException.class)
    public ResponseEntity<ErrorResponse> handleJpaAndDataIntegrityExceptions(RuntimeException e) {
        log.error("Exception occurred: ", e);
        String errorMessage = e.getCause() != null ? e.getCause().getMessage() : e.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(errorMessage, System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    /**
     * Handles exceptions related to Security.
     */
    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<ErrorResponse> handleSecurityException(SecurityException e) {
        log.error("SecurityException: ", e);
        ErrorResponse errorResponse = new ErrorResponse("Security exception: " + e.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

    /**
     * Handles MethodArgumentNotValidException.
     *
     * @param e the caught MethodArgumentNotValidException.
     * @return a ResponseEntity with error details and HTTP status code.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        List<ErrorsField> errors = e.getFieldErrors().stream().map(fieldError -> new ErrorsField(fieldError.getField(), fieldError.getDefaultMessage())).toList();
        ErrorResponseValid errorResponse = new ErrorResponseValid("Data not valid", System.currentTimeMillis(), errors);
        return ResponseEntity.badRequest().body(errorResponse);
    }
    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<ErrorResponse> handlePersistenceException(PersistenceException e) {
        log.error("PersistenceException: ", e);
        ErrorResponse errorResponse = new ErrorResponse("Persistence exception: " + e.getCause().getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }


    public record ErrorsField(String field, String message) {
    }

    public record ErrorResponseValid(String message, long timestamp, List<ErrorsField> errorsField) {
    }

    public record ErrorResponse(String message, long timestamp) {
    }
}
