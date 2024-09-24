package com.trongdev.banking_system.mapper;

import com.trongdev.banking_system.dto.request.UserCreateRequest;
import com.trongdev.banking_system.dto.request.UserUpdateRequest;
import com.trongdev.banking_system.dto.response.UserResponse;
import com.trongdev.banking_system.entity.Role;
import com.trongdev.banking_system.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreateRequest request);
    @Mapping(target = "fullName", expression = "java(user.getFirstName() + \" \" + user.getLastName())")
    @Mapping(target = "roles", expression = "java(mapRolesToStrings(user.getRoles()))")
    UserResponse toUserResponse(User user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);

    // Phương thức ánh xạ tùy chỉnh
    default Set<String> mapRolesToStrings(Set<Role> roles) {
        return roles.stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }
}
