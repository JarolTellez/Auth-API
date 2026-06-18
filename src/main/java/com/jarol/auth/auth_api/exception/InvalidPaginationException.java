package com.jarol.auth.auth_api.exception;

import org.springframework.http.HttpStatus;

public class InvalidPaginationException extends BusinessException {
    public InvalidPaginationException(String message) {
        super(
                message,
                ErrorCode.INVALID_PAGINATION,
                HttpStatus.BAD_REQUEST);
    }
}
