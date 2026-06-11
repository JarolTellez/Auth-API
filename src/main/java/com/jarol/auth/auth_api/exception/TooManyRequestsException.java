package com.jarol.auth.auth_api.exception;

import org.springframework.http.HttpStatus;

public class TooManyRequestsException extends BusinessException{
    public TooManyRequestsException(){
        super(
                "Too many request. Please try again later.",
                ErrorCode.TOO_MANY_REQUESTS,
                HttpStatus.TOO_MANY_REQUESTS
        );
    }

    public TooManyRequestsException(String message){
        super(
                message,
                ErrorCode.TOO_MANY_REQUESTS,
                HttpStatus.TOO_MANY_REQUESTS
        );
    }
}
