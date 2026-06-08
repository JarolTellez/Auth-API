package com.jarol.auth.auth_api.exception;

import org.springframework.http.HttpStatus;

public class UserNotVerifiedException extends BusinessException{
    public UserNotVerifiedException(){
        super(
                "Your email address has not been verified",
                ErrorCode.USER_NOT_VERIFIED,
                HttpStatus.FORBIDDEN
        );
    }

    public UserNotVerifiedException(String message){
        super(
                message,
                ErrorCode.USER_NOT_VERIFIED,
                HttpStatus.FORBIDDEN
        );
    }
}
