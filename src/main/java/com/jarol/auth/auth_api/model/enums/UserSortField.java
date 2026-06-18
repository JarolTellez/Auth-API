package com.jarol.auth.auth_api.model.enums;

public enum UserSortField {
    ID("id"),
    USERNAME("username"),
    EMAIL("email"),
    CREATED_AT("createdAt"),
    UPDATED_AT("updatedAt"),
    LAST_LOGIN_AT("lastLoginAt");

    private final String field;

    UserSortField(String field) {
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
