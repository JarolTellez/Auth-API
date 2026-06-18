package com.jarol.auth.auth_api.dto.request;

import com.jarol.auth.auth_api.pagination.PaginationConstants;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

public record PaginationRequest(

        @Min(0)
        Integer page,

        @Min(1)
        @Max(PaginationConstants.MAX_PAGE_SIZE)
        Integer size,

        String sortBy,

        @Pattern(
                regexp = "ASC|DESC",
                flags = Pattern.Flag.CASE_INSENSITIVE,
                message = "direction must be ASC or DESC"
        )
        String direction
) {

    public int getPageOrDefault() {
        return page == null ? PaginationConstants.DEFAULT_PAGE : page;
    }

    public int getSizeOrDefault() {
        return size == null ? PaginationConstants.DEFAULT_SIZE : size;
    }

    public String getDirectionOrDefault() {
        return direction == null ? PaginationConstants.DEFAULT_DIRECTION : direction;
    }
}
