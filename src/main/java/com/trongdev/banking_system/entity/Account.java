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
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    User user;

    @OneToOne
    @JoinColumn(name = "bankId", referencedColumnName = "id")
    Bank bank;

    String number;
    long balance;
    int isActive;
    Timestamp createdAt;
    Timestamp updatedAt;
}
