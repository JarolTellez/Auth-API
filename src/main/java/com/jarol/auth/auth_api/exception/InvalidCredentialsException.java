package com.jarol.auth.auth_api.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends BusinessException{

    public  InvalidCredentialsException(){
        super(
                "Invalid email/username or password",
                ErrorCode.INVALID_CREDENTIALS,
                HttpStatus.UNAUTHORIZED
        );
    }
}
