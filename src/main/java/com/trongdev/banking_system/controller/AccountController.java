package com.trongdev.banking_system.controller;

import com.trongdev.banking_system.dto.request.AccountCreateRequest;
import com.trongdev.banking_system.dto.response.AccountDetailResponse;
import com.trongdev.banking_system.dto.response.AccountListResponse;
import com.trongdev.banking_system.dto.response.ApiResponse;
import com.trongdev.banking_system.dto.response.PaginatedResponse;
import com.trongdev.banking_system.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Tag(name = "Account Controller")
public class AccountController {
    AccountService accountService;

    @Operation(summary = "Add new account", description = "Send a request via this API to create new account")
    @PostMapping
    ApiResponse<AccountDetailResponse> create(@RequestBody AccountCreateRequest request){
        return ApiResponse.<AccountDetailResponse>builder()
                .code(2000)
                .result(accountService.create(request))
                .message("User create bank account successfully!")
                .build();
    }

    @Operation(summary = "Get list of accounts", description = "Send a request via this API to get account list by page")
    @GetMapping
    ApiResponse<PaginatedResponse<AccountListResponse>> getAll(@RequestParam(defaultValue = "1") int page){
        return ApiResponse.<PaginatedResponse<AccountListResponse>>builder()
                .code(1000)
                .result(accountService.getAll(page))
                .message("Admin get all bank account successfully!")
                .build();
    }

    @Operation(summary = "Get account detail", description = "Send a request via this API to get account information")
    @GetMapping("/{accountId}")
    ApiResponse<AccountDetailResponse> getDetail(@PathVariable("accountId") int id){
        return ApiResponse.<AccountDetailResponse>builder()
                .code(1000)
                .result(accountService.getDetail(id))
                .message("Admin get detail of an account successfully!")
                .build();
    }

    @Operation(summary = "Delete account", description = "Send a request via this API to de-active an account")
    @DeleteMapping("/{accountId}")
    ApiResponse<Void> delete(@PathVariable("accountId") int id){
        accountService.delete(id);
        return ApiResponse.<Void>builder()
                .code(999)
                .message("Admin delete an account successfully!")
                .build();
    }
}
