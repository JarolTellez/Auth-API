package com.jarol.auth.auth_api.exception;

import com.jarol.auth.auth_api.dto.response.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BussinesException.class)
    public ResponseEntity<ApiError> handleBussinessException(BussinesException ex, HttpServletRequest request) {
        log.warn("Bussiness error: {}", ex.getMessage());

        ApiError error = ApiError.builder()
                .status(ex.getStatus().value())
                .errorCode(ex.getErrorCode().name())
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(ex.getStatus()).body(error);
    }

    public ResponseEntity<ApiError> handleGenericException(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error", ex);

        ApiError error = ApiError.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorCode(ErrorCode.INTERNAL_SERVER_ERROR.name())
                .message("Unexpected internal server error")
                .timestamp(Instant.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.internalServerError().body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Map<String, String> errors = new HashMap<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.put(
                    fieldError.getField(),
                    fieldError.getDefaultMessage()
            );
        }
            log.warn("Validation error: {}", errors);

            ApiError error = ApiError.builder()
                    .status(HttpStatus.BAD_REQUEST.value())
                    .errorCode(ErrorCode.VALIDATION_ERROR.name())
                    .message(errors.toString())
                    .timestamp(Instant.now())
                    .path(request.getRequestURI())
                    .build();

            return ResponseEntity.badRequest().body(error);


    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        log.warn("Constraint violation: {}", ex.getMessage());

        ApiError error = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .errorCode(ErrorCode.INVALID_PARAMETERS.name())
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity
                .badRequest()
                .body(error);
    }
}

