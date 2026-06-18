package com.jarol.auth.auth_api.service;

import com.jarol.auth.auth_api.dto.request.PaginationRequest;
import com.jarol.auth.auth_api.dto.request.RegisterRequest;
import com.jarol.auth.auth_api.dto.request.AdminUpdateUserRequest;
import com.jarol.auth.auth_api.dto.response.AdminUserResponse;
import com.jarol.auth.auth_api.dto.response.PaginatedResponse;
import com.jarol.auth.auth_api.dto.response.UserResponse;
import com.jarol.auth.auth_api.model.User;

import java.util.UUID;

public interface IUserService {

User createUser(RegisterRequest request);

User getUserByIdentifier(String identifier);

PaginatedResponse<AdminUserResponse> getUsers(PaginationRequest request);

User saveUser(User user);

UserResponse updateUserStatus(UUID userId, boolean enabled);
AdminUserResponse updateUser(UUID userId, AdminUpdateUserRequest request);
}
