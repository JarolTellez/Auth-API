package com.jarol.auth.auth_api.mapper;

import com.jarol.auth.auth_api.dto.request.RegisterRequest;
import com.jarol.auth.auth_api.dto.response.AuthResponse;
import com.jarol.auth.auth_api.dto.response.UserResponse;
import com.jarol.auth.auth_api.model.Role;
import com.jarol.auth.auth_api.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Mapper interface for converting between User entities and DTOs.
 * <p>
 * Uses MapStruct to automatically generate mapping implementations.
 * Handles conversion for registration requests and user responses.
 */
@Mapper(componentModel = "spring")
public interface IUserMapper {


    /**
     * Maps a RegisterRequest DTO to User entity.
     * <p>
     * Some fields of User are ignored because they are managed by the system (Domain or Services)
     *
     * @param request the registration request DTO containing user input
     * @return a User entity with the mapped fields from the request
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "sessions", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "enabled", ignore = true)
    User registerRequestToUser(RegisterRequest request);

    @Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
    UserResponse userToUserResponse(User user);

    default Set<String> mapRoles(Set<Role> roles) {
        if (roles == null) return null;

        return roles.stream()
                .map(role -> role.getName().name())
                .collect(Collectors.toSet());
    }


}
