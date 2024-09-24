package com.trongdev.banking_system.service;

import com.trongdev.banking_system.dto.request.PermissionRequest;
import com.trongdev.banking_system.dto.response.PermissionResponse;
import com.trongdev.banking_system.entity.Permission;
import com.trongdev.banking_system.exception.AppException;
import com.trongdev.banking_system.exception.ErrorCode;
import com.trongdev.banking_system.mapper.PermissionMapper;
import com.trongdev.banking_system.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionResponse create(PermissionRequest request){
        if(!permissionRepository.findById(request.getName()).isEmpty())
            throw new AppException(ErrorCode.PERMISSION_EXISTED);

        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    public List<PermissionResponse> getAll(){
        var permissions = permissionRepository.findAll();
        return permissions.stream()
                .map(permissionMapper::toPermissionResponse).toList();
    }

    public void delete(String permission){
        permissionRepository.deleteById(permission);
    }
}
