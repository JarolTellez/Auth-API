package com.jarol.auth.auth_api.exception;

import org.springframework.http.HttpStatus;

public class InvalidTokenException extends BusinessException{

    public InvalidTokenException(){
        super(
                "Invalid token",
                ErrorCode.INVALID_TOKEN,
                HttpStatus.UNAUTHORIZED
        );
    }
}
