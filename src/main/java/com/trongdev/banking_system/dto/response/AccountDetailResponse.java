package com.trongdev.banking_system.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Time;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AccountDetailResponse {
    String username;
    String fullname;
    String bankname;
    String accountNumber;
    long balance;
    Timestamp createdAt;
    Timestamp updatedAt;
}
