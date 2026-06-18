package com.jarol.auth.auth_api.repository;

import com.jarol.auth.auth_api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.dnd.DragSourceMotionListener;
import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    Boolean existsByEmail(String email);
    Boolean existsByUsername(String username);

    Optional<User> findByEmailOrUsername(String email, String username);

    @Override
    Page<User> findAll(Pageable pageable);
}
