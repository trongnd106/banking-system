package com.trongdev.banking_system.controller;

import com.trongdev.banking_system.dto.request.UserCreateRequest;
import com.trongdev.banking_system.dto.request.UserUpdateRequest;
import com.trongdev.banking_system.dto.response.ApiResponse;
import com.trongdev.banking_system.dto.response.PaginatedResponse;
import com.trongdev.banking_system.dto.response.UserResponse;
import com.trongdev.banking_system.entity.User;
import com.trongdev.banking_system.service.UserService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {
    UserService userService;
    @PostMapping
    ApiResponse<UserResponse> createUser(@RequestBody UserCreateRequest request){
        return ApiResponse.<UserResponse>builder()
                .code(2000)
                .result(userService.create(request))
                .message("Create user successfully!")
                .build();
    }
    @GetMapping
    ApiResponse<PaginatedResponse<UserResponse>> getAllUsers(@RequestParam(defaultValue = "1") int page){
        return ApiResponse.<PaginatedResponse<UserResponse>>builder()
                .code(1000)
                .result(userService.getAll(page))
                .message("Success get all users with page" + page)
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUserDetail(@PathVariable("userId") int id){
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .result(userService.getDetail(id))
                .message("Success get user detail!")
                .build();
    }

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo(){
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .result(userService.getMyInfo())
                .message("Get self-information successfully!")
                .build();
    }

    @PatchMapping("/{userId}")
    ApiResponse<UserResponse> updateUser(@PathVariable("userId") int id, @RequestBody UserUpdateRequest request){
        return ApiResponse.<UserResponse>builder()
                .code(2000)
                .result(userService.update(id, request))
                .message("Update user successfully!")
                .build();
    }

    @DeleteMapping("/{userId}")
    ApiResponse<Void> deleteUser(@PathVariable("userId") int id){
        userService.delete(id);
        return ApiResponse.<Void>builder()
                .code(999)
                .message("User deleted successfully!")
                .build();
    }

}
