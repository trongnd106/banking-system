package com.trongdev.banking_system.repository;

import com.trongdev.banking_system.entity.Transaction;
import com.trongdev.banking_system.entity.TransactionLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TransactionLogsRepository extends JpaRepository<TransactionLogs, Integer> {
    Optional<TransactionLogs> findByTransactionId(String id);
}
