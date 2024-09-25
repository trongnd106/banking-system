package com.trongdev.banking_system.mapper;

import com.trongdev.banking_system.dto.request.RoleRequest;
import com.trongdev.banking_system.dto.response.RoleResponse;
import com.trongdev.banking_system.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);
    RoleResponse toRoleResponse(Role role);
}
