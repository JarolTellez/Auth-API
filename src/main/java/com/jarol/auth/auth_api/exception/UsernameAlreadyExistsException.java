package com.jarol.auth.auth_api.exception;

import org.springframework.http.HttpStatus;

public class UsernameAlreadyExistsException extends BusinessException{

    public UsernameAlreadyExistsException(String username){
        super(
                "Username already exists: "+username,
                ErrorCode.USERNAME_ALREADY_EXISTS,
                HttpStatus.CONFLICT
        );

    }
}
