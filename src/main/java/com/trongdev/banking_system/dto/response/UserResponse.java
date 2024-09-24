package com.trongdev.banking_system.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    String username;
    String fullName;
    String address;
    LocalDate dob;
    String phone;
    String email;
    String identityCard;
    Set<String> roles;
}
