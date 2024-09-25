package com.trongdev.banking_system.service;

import com.trongdev.banking_system.dto.request.RoleRequest;
import com.trongdev.banking_system.dto.response.RoleResponse;
import com.trongdev.banking_system.entity.Role;
import com.trongdev.banking_system.mapper.RoleMapper;
import com.trongdev.banking_system.repository.PermissionRepository;
import com.trongdev.banking_system.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;

    public RoleResponse create(RoleRequest request){
        try {
            Role role =roleMapper.toRole(request);

            var permissions = permissionRepository.findAllById(request.getPermissions());
            role.setPermissions(new HashSet<>(permissions));

            role = roleRepository.save(role);
            return roleMapper.toRoleResponse(role);
        } catch(Exception e){
            throw new RuntimeException("Failed to create role, error message: " + e.getMessage());
        }
    }
    public List<RoleResponse> getAll(){
        return roleRepository.findAll().stream()
                .map(roleMapper::toRoleResponse)
                .toList();
    }
    public void delete(String role){
        roleRepository.deleteById(role);
    }
}
