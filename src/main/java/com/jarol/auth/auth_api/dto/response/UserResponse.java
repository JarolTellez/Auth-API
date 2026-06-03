package com.jarol.auth.auth_api.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;


public record UserResponse (
        UUID id,
        String username,
        String email,
        boolean enabled,
        Set<String> roles
) {

}
