package com.jarol.auth.auth_api.dto.response;

import com.jarol.auth.auth_api.model.enums.Browser;
import com.jarol.auth.auth_api.model.enums.DeviceType;
import com.jarol.auth.auth_api.model.enums.OS;

import java.time.Instant;
import java.util.UUID;

public record SessionResponse(
        UUID id,
        DeviceType deviceType,
        OS os,
        Browser browser,
        Instant createdAt,
        Instant expiresAt
){}
