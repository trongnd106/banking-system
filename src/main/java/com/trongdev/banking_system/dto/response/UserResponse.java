package com.trongdev.banking_system.dto.response;

import java.time.LocalDate;
import java.util.Set;

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
