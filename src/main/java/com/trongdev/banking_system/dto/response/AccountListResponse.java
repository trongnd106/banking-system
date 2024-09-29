package com.trongdev.banking_system.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class AccountListResponse {
    String userName;
    String bankName;
    String bankNumber;
    Timestamp createdAt;
}
