package com.jarol.auth.auth_api.exception;

import com.jarol.auth.auth_api.model.enums.EnumRole;
import org.springframework.http.HttpStatus;

public class RoleNotFoundException extends BusinessException {

    public RoleNotFoundException(EnumRole role) {
        super(
                "Role not found: " + role,
                ErrorCode.ROLE_NOT_FOUND,
                HttpStatus.NOT_FOUND
        );
    }

    public RoleNotFoundException() {
        super(
                "Role not found",
                ErrorCode.ROLE_NOT_FOUND,
                HttpStatus.NOT_FOUND
        );
    }
}
