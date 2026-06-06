package com.jarol.auth.auth_api.dto.response;

import java.util.Set;
import java.util.UUID;

public record AdminUserResponse(
        UUID id,
        String username,
        String email,
        boolean enabled,
        Set<RoleResponse> roles
) {
}
