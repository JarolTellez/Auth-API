package com.jarol.auth.auth_api.repository;

import com.jarol.auth.auth_api.model.Role;
import com.jarol.auth.auth_api.model.enums.EnumRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IRoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByName(EnumRole name);
}
