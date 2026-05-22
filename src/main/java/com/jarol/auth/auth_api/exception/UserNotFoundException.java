package com.jarol.auth.auth_api.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(String identifier){
        super(
                "User not found: "+identifier,
                ErrorCode.USER_NOT_FOUND,
                HttpStatus.NOT_FOUND
        );
    }
}
