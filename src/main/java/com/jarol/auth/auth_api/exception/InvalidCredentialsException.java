package com.jarol.auth.auth_api.exception;

import org.springframework.http.HttpStatus;

public class InvalidCredentialsException extends BusinessException{

    public  InvalidCredentialsException(){
        super(
                "Invalid email/username or password",
                ErrorCode.EMAIL_ALREADY_EXISTS,
                HttpStatus.UNAUTHORIZED
        );
    }
}
