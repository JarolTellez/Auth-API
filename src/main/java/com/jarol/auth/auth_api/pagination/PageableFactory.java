package com.jarol.auth.auth_api.pagination;

import com.jarol.auth.auth_api.dto.request.PaginationRequest;
import com.jarol.auth.auth_api.exception.InvalidPaginationException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PageableFactory {
    public Pageable create(
            PaginationRequest request,
            String sortField
    ) {

        Sort.Direction direction =
                Sort.Direction.ASC;


        try {

            direction =
                    Sort.Direction.fromString(
                            request.getDirectionOrDefault()
                    );

        } catch (IllegalArgumentException ex) {

            throw new InvalidPaginationException(
                    "Invalid direction"
            );
        }


        return PageRequest.of(
                request.getPageOrDefault(),
                request.getSizeOrDefault(),
                Sort.by(direction, sortField)
        );
    }
}
