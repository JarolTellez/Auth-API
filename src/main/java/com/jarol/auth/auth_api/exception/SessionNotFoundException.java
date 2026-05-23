package com.jarol.auth.auth_api.exception;

import com.jarol.auth.auth_api.model.Session;
import org.springframework.http.HttpStatus;

public class SessionNotFoundException extends BusinessException{

    public SessionNotFoundException(){
        super(
                "Session not found",
                ErrorCode.SESSION_NOT_FOUND,
                HttpStatus.NOT_FOUND
        );
    }
}
