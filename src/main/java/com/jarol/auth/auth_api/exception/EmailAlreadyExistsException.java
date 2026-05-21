package com.jarol.auth.auth_api.exception;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends BusinessException {

    public EmailAlreadyExistsException(String email){
        super(
                "Email already exists: " + email,
                ErrorCode.EMAIL_ALREADY_EXISTS,
                HttpStatus.CONFLICT
        );
    }
}
