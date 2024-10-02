package com.trongdev.banking_system.controller;

import com.trongdev.banking_system.dto.request.PermissionRequest;
import com.trongdev.banking_system.dto.response.ApiResponse;
import com.trongdev.banking_system.dto.response.PermissionResponse;
import com.trongdev.banking_system.service.PermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Permission Controller")
public class PermissionController {
    PermissionService permissionService;

    @Operation(summary = "Add new permission", description = "Send a request via this API to create a permission")
    @PostMapping
    ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request){
        return ApiResponse.<PermissionResponse>builder()
                .code(2000)
                .result(permissionService.create(request))
                .message("Create 1 permission successfully")
                .build();
    }

    @Operation(summary = "Get list of permission", description = "Send a request via this API to get list of permission")
    @GetMapping
    ApiResponse<List<PermissionResponse>> getAll(){
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .message("Get all permissions successfully")
                .build();
    }

    @Operation(summary = "Delete permission", description = "Send a request via this API to remove a permission")
    @DeleteMapping("/{permission}")
    ApiResponse<Void> delete(@PathVariable String permission){
        permissionService.delete(permission);
        return ApiResponse.<Void>builder().code(999).message("Delete permission successfully").build();
    }
}
