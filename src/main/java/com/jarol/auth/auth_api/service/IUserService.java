package com.jarol.auth.auth_api.service;

import com.jarol.auth.auth_api.dto.request.RegisterRequest;
import com.jarol.auth.auth_api.dto.response.UserResponse;
import com.jarol.auth.auth_api.model.User;

import java.util.UUID;

public interface IUserService {

User createUser(RegisterRequest request);

User getByIdentifier(String identifier);
}
