package com.jarol.auth.auth_api.controller;

import com.jarol.auth.auth_api.dto.request.AdminUpdateUserRequest;
import com.jarol.auth.auth_api.dto.request.PaginationRequest;
import com.jarol.auth.auth_api.dto.request.UpdateUserStatusRequest;
import com.jarol.auth.auth_api.dto.response.AdminUserResponse;
import com.jarol.auth.auth_api.dto.response.PaginatedResponse;
import com.jarol.auth.auth_api.dto.response.UserResponse;
import com.jarol.auth.auth_api.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ADMIN')")
@Validated
@RequiredArgsConstructor
public class AdminUserController {

    private final UserService userService;

    @PatchMapping("/{userId}/status")
    public ResponseEntity<UserResponse> updateUserStatus(@PathVariable UUID userId, @RequestBody @Valid UpdateUserStatusRequest request) {
        UserResponse response = userService.updateUserStatus(userId, request.enabled());

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<AdminUserResponse> updateUser(@PathVariable UUID userId, @RequestBody @Valid AdminUpdateUserRequest request) {

        AdminUserResponse response= userService.updateUser(userId,request);

        return ResponseEntity.ok(response);
    }


    @GetMapping
    public ResponseEntity<PaginatedResponse<AdminUserResponse>> getUsers(
            @Valid PaginationRequest request
            ) {

        return ResponseEntity.ok(
                userService.getUsers(request)
        );
    }

}
