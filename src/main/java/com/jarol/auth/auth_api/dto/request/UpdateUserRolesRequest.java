package com.jarol.auth.auth_api.dto.request;

import com.jarol.auth.auth_api.model.enums.EnumRole;

import java.util.Set;

public record UpdateUserRolesRequest(
       Set<EnumRole> roles
) {
}
