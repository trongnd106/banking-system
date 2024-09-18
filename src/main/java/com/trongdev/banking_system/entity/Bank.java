package com.trongdev.banking_system.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.UniqueElements;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @UniqueElements
    String name;

    @UniqueElements
    String code;

    String description;
}
