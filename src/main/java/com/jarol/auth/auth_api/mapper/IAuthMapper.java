package com.jarol.auth.auth_api.mapper;

import com.jarol.auth.auth_api.dto.response.AuthResponse;
import com.jarol.auth.auth_api.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Mapper interface for converting between User entities, tokens and DTOs.
 * <p>
 * Uses MapStruct to automatically generate mapping implementations.
 * Handles conversion for Auth data for response like User along with authentication Tokens (AccessToken and RefreshToken) to user responses.
 */
@Mapper(componentModel = "spring", uses = IUserMapper.class)
public interface IAuthMapper {

    /**
     * Maps a User entity along with authentication tokens to an AuthResponse DTO.
     * <p>
     * This method is useful for generating the response returned after a successful registration, login
     * or token refresh, including the user data and token information.
     *
     * @param user the User entity to get information to include in the response
     * @param accessToken the JWT access token issued for the user
     * @param refreshToken the refresh token issued for the user
     * @param refreshTokenExpiresAt the expiration timestamp of the refresh token
     * @return an AuthResponse DTO containing user details and token information
     */
    @Mapping(target = "user", source = "user")
    @Mapping(target = "accessToken", source = "accessToken")
    @Mapping(target = "refreshToken", source = "refreshToken")
    @Mapping(target = "refreshTokenExpiresAt", source = "refreshTokenExpiresAt")
    AuthResponse userToAuthResponse(User user, String accessToken, String refreshToken, Instant refreshTokenExpiresAt);
}
