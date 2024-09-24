package com.trongdev.banking_system.mapper;

import com.trongdev.banking_system.dto.request.PermissionRequest;
import com.trongdev.banking_system.dto.response.PermissionResponse;
import com.trongdev.banking_system.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
