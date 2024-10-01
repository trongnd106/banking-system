package com.trongdev.banking_system.service;

import com.trongdev.banking_system.dto.request.TransactionRequest;
import com.trongdev.banking_system.dto.response.TransactionResponse;
import com.trongdev.banking_system.entity.Account;
import com.trongdev.banking_system.entity.Transaction;
import com.trongdev.banking_system.entity.TransactionLogs;
import com.trongdev.banking_system.mapper.TransactionMapper;
import com.trongdev.banking_system.repository.AccountRepository;
import com.trongdev.banking_system.repository.TransactionLogsRepository;
import com.trongdev.banking_system.repository.TransactionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TransactionService {
    TransactionLogsRepository transactionLogsRepository;
    TransactionRepository transactionRepository;
    AccountRepository accountRepository;
    TransactionMapper transactionMapper;

    @Transactional
    public TransactionResponse create(TransactionRequest request){
        Transaction transaction = new Transaction();
        TransactionLogs transactionLogs = new TransactionLogs();
        try {
            Account senderAccount = accountRepository.findByNumberAndBank_Name
                            (request.getSenderNumber(),request.getSenderBank())
                    .orElseThrow(() -> new RuntimeException("Sender account not found"));
            Account receiverAccount = accountRepository.findByNumberAndBank_Name
                            (request.getReceiverNumber(),request.getReceiverBank())
                    .orElseThrow(() -> new RuntimeException("Sender account not found"));

            if(senderAccount.getBalance() < request.getAmount())
                throw new RuntimeException("Insufficient balance");

            long fee = request.getAmount()/10000;
            long amount = request.getAmount();
            Timestamp time = Timestamp.from(Instant.now());

            senderAccount.setBalance(senderAccount.getBalance() - amount - fee);
            senderAccount.setUpdatedAt(time);
            receiverAccount.setBalance(receiverAccount.getBalance() + amount);
            receiverAccount.setUpdatedAt(time);

            transaction = Transaction.builder()
                    .senderNumber(senderAccount.getNumber())
                    .senderBank(senderAccount.getBank().getName())
                    .receiverNumber(receiverAccount.getNumber())
                    .receiverBank(receiverAccount.getBank().getName())
                    .amount(amount)
                    .fee(fee)
                    .time(time)
                    .type(request.getType())
                    .build();
            transactionRepository.save(transaction);

            transactionLogs = TransactionLogs.builder()
                    .transaction(transaction)
                    .status("Success")
                    .isActive(1)
                    .remarks("")
                    .build();
            transactionLogsRepository.save(transactionLogs);

            return transactionMapper.toTransactionResponse(transaction);

        } catch(Exception exception){
            transactionLogs = TransactionLogs.builder()
                    .transaction(transaction)
                    .status("Fail")
                    .isActive(1)
                    .remarks("")
                    .build();
            transactionLogsRepository.save(transactionLogs);
            throw new RuntimeException("Transaction failed", exception);
        }
    }

}
