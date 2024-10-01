package com.trongdev.banking_system.controller;

import com.trongdev.banking_system.dto.request.TransactionRequest;
import com.trongdev.banking_system.dto.response.ApiResponse;
import com.trongdev.banking_system.dto.response.TransactionResponse;
import com.trongdev.banking_system.service.TransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TransactionController {
    TransactionService transactionService;

    @PostMapping
    ApiResponse<TransactionResponse> create(@RequestBody TransactionRequest request){
        return ApiResponse.<TransactionResponse>builder()
                .code(2000)
                .result(transactionService.create(request))
                .message("Transaction execute successfully!")
                .build();
    }
}
