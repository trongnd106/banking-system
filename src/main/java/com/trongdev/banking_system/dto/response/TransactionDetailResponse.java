package com.trongdev.banking_system.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TransactionDetailResponse {
    String transactionId;
    String senderUser;
    String senderNumber;
    String senderBank;
    String receiverUser;
    String receiverNumber;
    String receiverBank;
    long amount;
    long fee;
    String status;
    String remarks;
    Timestamp time;
}
