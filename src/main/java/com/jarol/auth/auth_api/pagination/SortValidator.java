package com.jarol.auth.auth_api.pagination;

import com.jarol.auth.auth_api.exception.InvalidPaginationException;
import com.jarol.auth.auth_api.model.enums.UserSortField;

import java.util.Arrays;

public class SortValidator {
    private SortValidator() {
    }

    public static String validate(String sortBy) {

        if (sortBy == null || sortBy.isBlank()) {
            return UserSortField.CREATED_AT.getField();
        }

        return Arrays.stream(UserSortField.values())
                .filter(field ->
                        field.name().equalsIgnoreCase(sortBy))
                .findFirst()
                .orElseThrow(() ->
                        new InvalidPaginationException(
                                "Invalid sort field: " + sortBy
                        ))
                .getField();
    }
}
