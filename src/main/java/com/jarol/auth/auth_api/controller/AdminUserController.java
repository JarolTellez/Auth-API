package com.jarol.auth.auth_api.controller;

import com.jarol.auth.auth_api.dto.request.UpdateUserRolesRequest;
import com.jarol.auth.auth_api.dto.request.UpdateUserStatusRequest;
import com.jarol.auth.auth_api.dto.response.AdminUserResponse;
import com.jarol.auth.auth_api.dto.response.UserResponse;
import com.jarol.auth.auth_api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @PatchMapping("/{userId}/status")
    public ResponseEntity<UserResponse> updateUserStatus(@PathVariable UUID userId, @RequestBody UpdateUserStatusRequest request) {
        UserResponse response = userService.updateUserStatus(userId, request.enabled());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{userid}/roles")
    public ResponseEntity<AdminUserResponse> updateRoles(@PathVariable UUID userId, @RequestBody UpdateUserRolesRequest request) {

    }

}
