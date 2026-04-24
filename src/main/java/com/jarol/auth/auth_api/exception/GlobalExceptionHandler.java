package com.jarol.auth.auth_api.exception;

import com.jarol.auth.auth_api.dto.response.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public ResponseEntity<ApiError> handleBussinessException(BussinesException ex, HttpServletRequest request){
        log.warn("Bussiness error: {}", ex.getMessage());

        ApiError error = ApiError.builder()
                .status(ex.getStatus().value())
                .errorCode(ex.getErrorCode())
                .message(ex.getMessage())
                .timestamp(Instant.now())
                .path(request.getRequestURI())
                .build();

        return ResponseEntity.status(ex.getStatus()).body(error);
    }
}
