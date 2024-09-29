package com.trongdev.banking_system.service;

import com.trongdev.banking_system.dto.request.AccountCreateRequest;
import com.trongdev.banking_system.dto.response.AccountDetailResponse;
import com.trongdev.banking_system.entity.Account;
import com.trongdev.banking_system.entity.Bank;
import com.trongdev.banking_system.entity.User;
import com.trongdev.banking_system.exception.AppException;
import com.trongdev.banking_system.exception.ErrorCode;
import com.trongdev.banking_system.helper.AccountNumberGenerator;
import com.trongdev.banking_system.mapper.AccountMapper;
import com.trongdev.banking_system.repository.AccountRepository;
import com.trongdev.banking_system.repository.BankRepository;
import com.trongdev.banking_system.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.sql.Timestamp;
import java.time.Instant;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AccountService {
    AccountRepository accountRepository;
    UserRepository userRepository;
    BankRepository bankRepository;
    AccountNumberGenerator accountNumberGenerator;
    AccountMapper accountMapper;

    public AccountDetailResponse create(AccountCreateRequest request){
        String currentUsername = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(currentUsername).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_EXISTED)
        );
        Bank bank = bankRepository.findById(request.getBankId()).orElseThrow(
                () -> new AppException(ErrorCode.BANK_NOT_EXISTED)
        );
        String generatedAccountNumber = accountNumberGenerator.generateAccountNumber(bank);
        Timestamp currentTime = Timestamp.from(Instant.now());
        Account account = Account.builder()
                .user(user)
                .bank(bank)
                .number(generatedAccountNumber)
                .balance(0)
                .createdAt(currentTime)
                .updatedAt(currentTime)
                .build();
        return accountMapper.toAccountResponse(accountRepository.save(account));
    }
}
