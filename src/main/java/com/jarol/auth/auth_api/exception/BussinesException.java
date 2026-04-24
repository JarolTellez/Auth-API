package com.jarol.auth.auth_api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public abstract class BussinesException extends RuntimeException {

    private final ErrorCode errorCode;
    private final HttpStatus status;

    protected BussinesException(String message, ErrorCode errorCode, HttpStatus status){
        super(message);
        this.errorCode=errorCode;
        this.status=status;
    }
}
