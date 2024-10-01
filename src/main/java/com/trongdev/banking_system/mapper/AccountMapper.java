package com.trongdev.banking_system.mapper;

import com.trongdev.banking_system.dto.request.AccountCreateRequest;
import com.trongdev.banking_system.dto.response.AccountDetailResponse;
import com.trongdev.banking_system.dto.response.AccountListResponse;
import com.trongdev.banking_system.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    default AccountDetailResponse toAccountResponse(Account account){
        return AccountDetailResponse.builder()
                .accountNumber(account.getNumber())
                .username(account.getUser().getUsername())
                .fullname(account.getUser().getFirstName() + " " + account.getUser().getLastName())
                .bankname(account.getBank().getName())
                .balance(account.getBalance())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt())
                .build();
    };

    default AccountListResponse toListResponse(Account account){
        return AccountListResponse.builder()
                .userName(account.getUser().getUsername())
                .bankName(account.getBank().getName())
                .bankNumber(account.getNumber())
                .createdAt(account.getCreatedAt())
                .build();
    }
}
