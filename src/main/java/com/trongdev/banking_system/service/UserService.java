package com.trongdev.banking_system.service;

import com.trongdev.banking_system.dto.request.UserCreateRequest;
import com.trongdev.banking_system.entity.User;
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

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    public User createUser(UserCreateRequest request){
        if(userRepository.existsByUsername(request.getUsername()))
            throw new RuntimeException(ErrorCode.USER_EXISTED.getMessage());

        User user = userMapper.toUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        Timestamp currentTime = Timestamp.from(Instant.now());
        user.setCreatedAt(currentTime);
        user.setUpdatedAt(currentTime);
        user.setIsActive(1);

        return userRepository.save(user);
    }
}
