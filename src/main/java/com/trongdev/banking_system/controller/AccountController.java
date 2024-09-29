package com.trongdev.banking_system.controller;

import com.trongdev.banking_system.dto.request.AccountCreateRequest;
import com.trongdev.banking_system.dto.response.AccountDetailResponse;
import com.trongdev.banking_system.dto.response.ApiResponse;
import com.trongdev.banking_system.service.AccountService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AccountController {
    AccountService accountService;

    @PostMapping
    ApiResponse<AccountDetailResponse> create(@RequestBody AccountCreateRequest request){
        return ApiResponse.<AccountDetailResponse>builder()
                .code(2000)
                .result(accountService.create(request))
                .message("User create bank account successfully!")
                .build();
    }
}
