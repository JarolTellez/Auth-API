package com.jarol.auth.auth_api.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Getter
@Builder
public class ApiError {

    private  int status;
    private String errorCode;
    private String message;
    private Instant timestamp;
    private String path;
}
