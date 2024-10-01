package com.trongdev.banking_system.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    String senderNumber;
    String receiverNumber;
    String senderBank;
    String receiverBank;
    long amount;
    long fee;
    Timestamp time;
    String type;
}
