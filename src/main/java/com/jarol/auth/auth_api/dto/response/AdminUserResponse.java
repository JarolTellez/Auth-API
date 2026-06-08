package com.jarol.auth.auth_api.dto.response;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record AdminUserResponse(
        UUID id,
        String username,
        String email,
        boolean enabled,
        boolean verified,
        Instant verifiedAT,
        Instant createdAt,
        Instant updatedAt,
        Set<RoleResponse> roles
) {
}
