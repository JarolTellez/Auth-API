package com.jarol.auth.auth_api.dto.response;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class UserResponse {
    private UUID id;
    private String name;
    private String username;
    private String email;
    private Set<String> roles;
}
