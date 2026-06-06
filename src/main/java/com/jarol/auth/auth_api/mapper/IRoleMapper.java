package com.jarol.auth.auth_api.mapper;

import com.jarol.auth.auth_api.dto.response.RoleResponse;
import com.jarol.auth.auth_api.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Mapper(componentModel="spring")
public interface IRoleMapper {

    @Mapping(target = "name", expression="java(role.getName().name())")
    RoleResponse roleToRoleResponse(Role role);
}
