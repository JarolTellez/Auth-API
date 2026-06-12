package com.jarol.auth.auth_api.exception;

import org.springframework.http.HttpStatus;

public class AccountLockedException extends BusinessException{

    public AccountLockedException(){
        super(
                "Account is locked. Try again later",
                ErrorCode.ACCOUNT_LOCKED,
                HttpStatus.TOO_MANY_REQUESTS
        );
    }
}
