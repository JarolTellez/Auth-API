package com.jarol.auth.auth_api.dto.response;

import java.util.UUID;

public record RoleResponse(
        UUID id,
        String name
) {
}
