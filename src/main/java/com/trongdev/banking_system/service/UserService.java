package com.trongdev.banking_system.service;

import com.trongdev.banking_system.dto.request.UserCreateRequest;
import com.trongdev.banking_system.dto.request.UserUpdateRequest;
import com.trongdev.banking_system.dto.response.PaginatedResponse;
import com.trongdev.banking_system.dto.response.UserResponse;
import com.trongdev.banking_system.entity.Role;
import com.trongdev.banking_system.entity.User;
import com.trongdev.banking_system.exception.AppException;
import com.trongdev.banking_system.exception.ErrorCode;
import com.trongdev.banking_system.mapper.UserMapper;
import com.trongdev.banking_system.repository.RoleRepository;
import com.trongdev.banking_system.repository.UserRepository;
import com.trongdev.banking_system.utils.ConstValue;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    public UserResponse create(UserCreateRequest request){
        if(userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Timestamp currentTime = Timestamp.from(Instant.now());
        user.setCreatedAt(currentTime);
        user.setUpdatedAt(currentTime);
        user.setIsActive(1);

        Role staffRole = new Role();
        staffRole.setName("STAFF");
        user.setRoles(new HashSet<>(Collections.singletonList(staffRole)));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public PaginatedResponse<UserResponse> getAll(int page){
        var perPage = ConstValue.PER_PAGE;
        Pageable pageable = PageRequest.of(page - 1, perPage);

        Page<User> userPage = userRepository.findAllByIsActive(1, pageable);

        List<UserResponse> userResponses = userPage.getContent().stream()
                .map(userMapper::toUserResponse).toList();

        int totalPage = userPage.getTotalPages();
        int nextPage = page < totalPage ? page + 1 : 0;
        int prevPage = page > 1 ? page - 1 : 0;

        return PaginatedResponse.<UserResponse>builder()
                .totalPage(totalPage)
                .perPage(perPage)
                .curPage(page)
                .nextPage(nextPage)
                .prevPage(prevPage)
                .data(userResponses)
                .build();
    }

    public UserResponse getDetail(String id){
        var user = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found!")
        );
        return userMapper.toUserResponse(user);
    }

//    @PreAuthorize("hasAuthority('UPDATE_USER')")
    public UserResponse update(String userId, UserUpdateRequest request){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // todo: Fix after integrate Token authen&author
        // just user with permission UPDATE_ROLE can edit role
        if (request.getRoles() != null && !request.getRoles().isEmpty()) {
//            if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
//                    .anyMatch(auth -> auth.getAuthority().equals("UPDATE_ROLE"))) {
            Set<Role> roles = new HashSet<>();
            for (String roleName : request.getRoles()) {
                Role role = roleRepository.findById(roleName).orElseThrow(
                        () -> new AppException(ErrorCode.ROLE_NOT_EXISTED)
                );
                roles.add(role);
            }
            user.setRoles(roles);
//            } else {
//                throw new AppException(ErrorCode.PERMISSION_DENIED);
//            }
        }
        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void delete(String userId){
        userRepository.deactivateUser(userId);
    }
}
