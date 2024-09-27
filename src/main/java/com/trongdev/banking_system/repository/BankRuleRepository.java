package com.trongdev.banking_system.repository;

import com.trongdev.banking_system.entity.Bank;
import com.trongdev.banking_system.entity.BankRule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankRuleRepository extends JpaRepository<BankRule, Integer> {
    Optional<BankRule> findByBank(Bank bank);
}
