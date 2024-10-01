package com.trongdev.banking_system.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class TransactionRequest {
    String senderNumber;
    String senderBank;
    String receiverNumber;
    String receiverBank;
    long amount;
    String type;   // Transfer or Withdraw
}
