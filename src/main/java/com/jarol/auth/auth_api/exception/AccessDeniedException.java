package com.jarol.auth.auth_api.exception;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends BusinessException{

    public AccessDeniedException(){
        super(
                "Access denied",
                ErrorCode.ACCESS_DENIED,
                HttpStatus.FORBIDDEN
        );
    }
}
