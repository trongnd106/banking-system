package com.trongdev.banking_system.repository;

import com.trongdev.banking_system.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {
    @Query("SELECT t FROM Transaction t " +
            "JOIN Account senderAccount ON t.senderNumber = senderAccount.number " +
            "JOIN Account receiverAccount ON t.receiverNumber = receiverAccount.number " +
            "WHERE senderAccount.user.id = :userId OR receiverAccount.user.id = :userId")
    Page<Transaction> findAllByUserId(@Param("userId") int userId, Pageable pageable);
}
