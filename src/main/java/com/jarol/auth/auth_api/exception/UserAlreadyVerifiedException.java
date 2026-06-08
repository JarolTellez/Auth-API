package com.jarol.auth.auth_api.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyVerifiedException extends BusinessException{

    public  UserAlreadyVerifiedException() {
        super(
                "Account is already verified",
                ErrorCode.USER_ALREADY_VERIFIED,
                HttpStatus.CONFLICT
        );
    }
}
