package com.jarol.auth.auth_api.exception;

import org.springframework.http.HttpStatus;

public class TokenNotFoundException extends  BusinessException{
    public TokenNotFoundException(){
        super(
                "Token not found",
                ErrorCode.TOKEN_NOT_FOUND,
                HttpStatus.NOT_FOUND
        );
    }

    public TokenNotFoundException(String message){
        super(
                message,
                ErrorCode.TOKEN_NOT_FOUND,
                HttpStatus.NOT_FOUND
        );
    }
}
