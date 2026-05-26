package com.jarol.auth.auth_api.service;

import com.jarol.auth.auth_api.dto.request.RegisterRequest;
import com.jarol.auth.auth_api.exception.EmailAlreadyExistsException;
import com.jarol.auth.auth_api.exception.RoleNotFoundException;
import com.jarol.auth.auth_api.exception.UserNotFoundException;
import com.jarol.auth.auth_api.exception.UsernameAlreadyExistsException;
import com.jarol.auth.auth_api.mapper.IUserMapper;
import com.jarol.auth.auth_api.model.Role;
import com.jarol.auth.auth_api.model.User;
import com.jarol.auth.auth_api.model.enums.EnumRole;
import com.jarol.auth.auth_api.repository.IRoleRepository;
import com.jarol.auth.auth_api.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IUserMapper userMapper;
    private final IRoleRepository roleRepository;

    @Override
    public User createUser(RegisterRequest request) {

        String normalizedEmail = normalizeIdentifier(request.getEmail());
        String normalizedUsername = normalizeIdentifier(request.getUsername());
        if (userRepository.existsByEmail(normalizedEmail)) {
            throw new EmailAlreadyExistsException(request.getEmail());
        }

        if (userRepository.existsByUsername(normalizedUsername)) {
            throw new UsernameAlreadyExistsException(request.getUsername());
        }


        User user = userMapper.registerRequestToUser(request);
        user.setEmail(normalizedEmail);
        user.setUsername(normalizedUsername);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Role roleUser = roleRepository.findByName(EnumRole.USER)
                .orElseThrow(() -> new RoleNotFoundException(EnumRole.USER));

        user.setRoles(Set.of(roleUser));

        return userRepository.save(user);


    }

    @Override
    public User getUserByIdentifier(String identifier) {
        String normalizedIdentifier=normalizeIdentifier(identifier);
        return userRepository
                .findByEmailOrUsername(normalizedIdentifier, normalizedIdentifier)
                .orElseThrow(() ->
                        new UserNotFoundException(identifier)
                );
    }

    private String normalizeIdentifier(String identifier) {
        return identifier.trim().toLowerCase();
    }

}
