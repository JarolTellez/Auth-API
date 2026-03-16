package com.jarol.auth.auth_api.repository;

import com.jarol.auth.auth_api.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ISessionRepository extends JpaRepository<Session, UUID> {


    Optional<Session> findByRefreshTokenHash(String token);
}
