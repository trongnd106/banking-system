package com.trongdev.banking_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.trongdev.banking_system.entity.InvalidToken;

public interface InvalidTokenRepository extends JpaRepository<InvalidToken, String> {
}
