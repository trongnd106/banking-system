package com.trongdev.banking_system.controller;

import com.trongdev.banking_system.dto.request.RoleRequest;
import com.trongdev.banking_system.dto.response.ApiResponse;
import com.trongdev.banking_system.dto.response.RoleResponse;
import com.trongdev.banking_system.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Role Controller")
public class RoleController {
    RoleService roleService;

    @Operation(summary = "Add new role", description = "Send a request via this API to create a role")
    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request){
        return ApiResponse.<RoleResponse>builder()
                .code(2000)
                .result(roleService.create(request))
                .message("Create a role with permissions successfully!")
                .build();
    }

    @Operation(summary = "Get list of role", description = "Send a request via this API to get role list by page")
    @GetMapping
    ApiResponse<List<RoleResponse>> getAll(){
        return ApiResponse.<List<RoleResponse>>builder()
                .code(1000)
                .result(roleService.getAll())
                .message("Get role lists successfully!")
                .build();
    }

    @Operation(summary = "Delete role", description = "Send a request via this API to remove a role")
    @DeleteMapping("/{role}")
    ApiResponse<Void> delete(@PathVariable String role){
        roleService.delete(role);
        return ApiResponse.<Void>builder()
                .code(999)
                .message("Delete role successfully!")
                .build();
    }

}
