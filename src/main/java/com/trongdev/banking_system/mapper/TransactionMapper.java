package com.trongdev.banking_system.mapper;

import com.trongdev.banking_system.dto.response.TransactionDetailResponse;
import com.trongdev.banking_system.dto.response.TransactionResponse;
import com.trongdev.banking_system.entity.Account;
import com.trongdev.banking_system.entity.Transaction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    default TransactionResponse toTransactionResponse(Transaction transaction){
        return TransactionResponse.builder()
                .transactionId(transaction.getId())
                .senderNumber(transaction.getSenderNumber())
                .senderBank(transaction.getSenderBank())
                .receiverNumber(transaction.getReceiverNumber())
                .receiverBank(transaction.getReceiverBank())
                .amount(transaction.getAmount())
                .fee(transaction.getFee())
                .time(transaction.getTime())
                .build();
    }
}
