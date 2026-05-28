package com.jarol.auth.auth_api.exception;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends BusinessException{

    public AccessDeniedException(String message){
        super(
                "Access denied "+message,
                ErrorCode.ACCESS_DENIED,
                HttpStatus.FORBIDDEN
        );
    }
}
