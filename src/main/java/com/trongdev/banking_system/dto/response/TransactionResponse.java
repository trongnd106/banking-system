package com.trongdev.banking_system.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TransactionResponse {
    String transactionId;
    String senderNumber;
    String senderBank;
    String receiverNumber;
    String receiverBank;
    long amount;
    long fee;
    Timestamp time;
}
