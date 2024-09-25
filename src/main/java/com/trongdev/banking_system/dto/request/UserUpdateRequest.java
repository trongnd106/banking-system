package com.trongdev.banking_system.dto.request;

import com.trongdev.banking_system.entity.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserUpdateRequest {
    @Size(min = 8, message = "PASSWORD_INVALID")
    String password;

    String firstName;
    String lastName;
    String address;

    @Size(min = 18, message = "DOB_INVALID")
    LocalDate dob;

    String phone;

    @Email(message = "EMAIL_INVALID")
    String email;

    Set<String> roles;
}












