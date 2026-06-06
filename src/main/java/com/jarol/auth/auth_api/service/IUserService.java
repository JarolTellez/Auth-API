package com.jarol.auth.auth_api.service;

import com.jarol.auth.auth_api.dto.request.RegisterRequest;
import com.jarol.auth.auth_api.dto.request.UpdateUserRolesRequest;
import com.jarol.auth.auth_api.dto.response.AdminUserResponse;
import com.jarol.auth.auth_api.dto.response.UserResponse;
import com.jarol.auth.auth_api.model.User;

import java.util.UUID;

public interface IUserService {

User createUser(RegisterRequest request);

User getUserByIdentifier(String identifier);

UserResponse updateUserStatus(UUID userId, boolean enabled);
AdminUserResponse updateUserRoles(UUID userId, UpdateUserRolesRequest request);
}
