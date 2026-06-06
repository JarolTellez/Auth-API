package com.jarol.auth.auth_api.service;

import com.jarol.auth.auth_api.dto.request.RegisterRequest;
import com.jarol.auth.auth_api.dto.request.AdminUpdateUserRequest;
import com.jarol.auth.auth_api.dto.response.AdminUserResponse;
import com.jarol.auth.auth_api.dto.response.UserResponse;
import com.jarol.auth.auth_api.exception.*;
import com.jarol.auth.auth_api.mapper.IUserMapper;
import com.jarol.auth.auth_api.model.Role;
import com.jarol.auth.auth_api.model.User;
import com.jarol.auth.auth_api.model.enums.EnumRole;
import com.jarol.auth.auth_api.repository.IRoleRepository;
import com.jarol.auth.auth_api.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IUserMapper userMapper;
    private final IRoleRepository roleRepository;

    @Override
    public User createUser(RegisterRequest request) {

        String normalizedEmail = normalizeIdentifier(request.email());
        String normalizedUsername = normalizeIdentifier(request.username());
        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new EmailAlreadyExistsException(request.email());
        }

        if (userRepository.existsByUsername(normalizedUsername)) {
            throw new UsernameAlreadyExistsException(request.username());
        }


        User user = userMapper.registerRequestToUser(request);
        user.setEmail(normalizedEmail);
        user.setUsername(normalizedUsername);

        user.setPassword(passwordEncoder.encode(request.password()));

        Role roleUser = roleRepository.findByName(EnumRole.USER)
                .orElseThrow(() -> new RoleNotFoundException(EnumRole.USER));

        user.setRoles(Set.of(roleUser));

        return userRepository.save(user);


    }

    @Override
    public User getUserByIdentifier(String identifier) {
        String normalizedIdentifier = normalizeIdentifier(identifier);
        return userRepository
                .findByEmailOrUsername(normalizedIdentifier, normalizedIdentifier)
                .orElseThrow(() ->
                        new UserNotFoundException(identifier)
                );
    }

    @Override
    public UserResponse updateUserStatus(UUID userId, boolean enabled) {
        User user = findUser(userId);

        if (user.isEnabled() == enabled) {
            return userMapper.userToUserResponse(user);
        }

        user.setEnabled(enabled);

        userRepository.save(user);

        return userMapper.userToUserResponse(user);

    }

    @Override
    public AdminUserResponse updateUser(UUID userId, AdminUpdateUserRequest request) {
        User user = findUser(userId);

        Set<Role> roles = new HashSet<>(
                roleRepository.findAllById(request.roleIds())
        );


        if(request.roleIds().size() != roles.size()){
            throw new TokenExpiredException();
        }

        user.setRoles(roles);
        user.setUsername(request.username());
        user.setEmail(request.email());
        User userResponse=userRepository.save(user);

        return userMapper.userToAdminUserResponse(user);
    }

    private String normalizeIdentifier(String identifier) {
        return identifier.trim().toLowerCase();
    }

    private User findUser(UUID userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId.toString()));
    }


}
