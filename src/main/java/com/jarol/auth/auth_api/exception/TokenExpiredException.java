package com.jarol.auth.auth_api.exception;

import org.springframework.http.HttpStatus;

public class TokenExpiredException extends BusinessException{
    public TokenExpiredException(){
        super(
                "Token has expired",
                ErrorCode.TOKEN_EXPIRED,
                HttpStatus.UNAUTHORIZED
        );
    }
}
