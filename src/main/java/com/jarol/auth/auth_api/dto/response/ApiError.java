package com.jarol.auth.auth_api.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;


public record ApiError(
        int status,
        String errorCode,
        String message,
        Instant timestamp,
        String path,
        Object details
) {

}
