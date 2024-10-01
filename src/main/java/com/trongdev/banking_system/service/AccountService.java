package com.trongdev.banking_system.service;

import com.trongdev.banking_system.dto.request.AccountCreateRequest;
import com.trongdev.banking_system.dto.response.AccountDetailResponse;
import com.trongdev.banking_system.dto.response.AccountListResponse;
import com.trongdev.banking_system.dto.response.PaginatedResponse;
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
import com.trongdev.banking_system.utils.ConstValue;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.Constants;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

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

    @PreAuthorize("hasAuthority('CREATE_ACCOUNT')")
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
                .isActive(1)
                .createdAt(currentTime)
                .updatedAt(currentTime)
                .build();
        return accountMapper.toAccountResponse(accountRepository.save(account));
    }

    @PreAuthorize("hasAuthority('VIEW_ACCOUNT_LIST')")
    public PaginatedResponse<AccountListResponse> getAll(int page){
        var perPage = ConstValue.PER_PAGE;
        Pageable pageable = PageRequest.of(page - 1, perPage);
        Page<Account> accountPage = accountRepository.findAll(pageable);
        List<AccountListResponse> accountResponse = accountPage.getContent().stream()
                .map(accountMapper::toListResponse).toList();

        int totalPage = accountPage.getTotalPages();
        int nextPage = page < totalPage ? page + 1 : 0;
        int prevPage = page > 1 ? page - 1 : 0;

        return PaginatedResponse.<AccountListResponse>builder()
                .totalPage(totalPage)
                .perPage(perPage)
                .curPage(page)
                .nextPage(nextPage)
                .prevPage(prevPage)
                .data(accountResponse)
                .build();
    }
}
