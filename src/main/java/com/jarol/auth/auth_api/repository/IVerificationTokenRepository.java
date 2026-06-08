package com.jarol.auth.auth_api.repository;

import com.jarol.auth.auth_api.model.User;
import com.jarol.auth.auth_api.model.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IVerificationTokenRepository extends JpaRepository<VerificationToken, UUID> {
    Optional<VerificationToken> findByUser(User user);
    Optional<VerificationToken> findByTokenHash(String token);
}
