package com.trongdev.banking_system.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.UniqueElements;

import java.sql.Timestamp;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @UniqueElements
    String username;

    String password;
    String firstName;
    String lastName;
    String address;
    Timestamp dob;
    String phone;
    String email;

    @UniqueElements
    String identityCard;

    int isActive;
    Timestamp createdAt;
    Timestamp updatedAt;

    @ManyToMany
    Set<Role> roles;
}
