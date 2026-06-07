package com.jarol.auth.auth_api.exception;

import org.springframework.http.HttpStatus;

public class UserDisabledException extends BusinessException{

    public UserDisabledException(String userIdentifier){
        super(
                "User disabled: "+ userIdentifier,
                ErrorCode.USER_DISABLED,
                HttpStatus.FORBIDDEN
        );
    }

    public UserDisabledException(){
        super(
                "User disabled",
                ErrorCode.USER_DISABLED,
                HttpStatus.FORBIDDEN
        );
    }
}
