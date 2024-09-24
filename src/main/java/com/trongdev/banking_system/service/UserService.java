package com.trongdev.banking_system.service;

import com.trongdev.banking_system.dto.request.UserCreateRequest;
import com.trongdev.banking_system.dto.request.UserUpdateRequest;
import com.trongdev.banking_system.dto.response.UserResponse;
import com.trongdev.banking_system.entity.Role;
import com.trongdev.banking_system.entity.User;
import com.trongdev.banking_system.exception.AppException;
import com.trongdev.banking_system.exception.ErrorCode;
import com.trongdev.banking_system.mapper.UserMapper;
import com.trongdev.banking_system.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
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

    public UserResponse create(UserCreateRequest request){
        if(userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Timestamp currentTime = Timestamp.from(Instant.now());
        user.setCreatedAt(currentTime);
        user.setUpdatedAt(currentTime);
        user.setIsActive(1);

//        if (user.getRoles() == null || user.getRoles().isEmpty()) {
//            Role userRole = new Role();
//            userRole.setName("USER");
//            user.setRoles(Set.of(userRole));
//        }

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public List<UserResponse> getAll(){
        return userRepository.findAllByIsActive(1).stream()
                .map(userMapper::toUserResponse).toList();
    }

    public UserResponse getDetail(String id){
        var user = userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("User not found!")
        );
        return userMapper.toUserResponse(user);
    }

    public UserResponse update(String userId, UserUpdateRequest request){
        User user = userRepository.findById(userId).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );

        userMapper.updateUser(user, request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    public void delete(String userId){
        userRepository.deactivateUser(userId);
    }
}
