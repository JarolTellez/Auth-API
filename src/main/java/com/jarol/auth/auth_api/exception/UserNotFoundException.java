package com.jarol.auth.auth_api.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class UserNotFoundException extends BusinessException {

    public UserNotFoundException(UUID id){
        super(
                "User not found with id: "+id,
                ErrorCode.USER_NOT_FOUND,
                HttpStatus.NOT_FOUND
        );
    }
}
