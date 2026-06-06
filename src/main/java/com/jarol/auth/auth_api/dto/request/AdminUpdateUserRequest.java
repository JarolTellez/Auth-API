package com.jarol.auth.auth_api.dto.request;

import java.util.Set;
import java.util.UUID;

public record AdminUpdateUserRequest(
        String username,
        String email,
        Set<UUID> roleIds
) {
}
