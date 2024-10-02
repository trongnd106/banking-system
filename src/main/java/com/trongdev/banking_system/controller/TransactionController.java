package com.trongdev.banking_system.controller;

import com.trongdev.banking_system.dto.request.TransactionRequest;
import com.trongdev.banking_system.dto.response.ApiResponse;
import com.trongdev.banking_system.dto.response.PaginatedResponse;
import com.trongdev.banking_system.dto.response.TransactionDetailResponse;
import com.trongdev.banking_system.dto.response.TransactionResponse;
import com.trongdev.banking_system.service.TransactionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/transaction")
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

    @GetMapping
    ApiResponse<PaginatedResponse<TransactionResponse>> getAll(@RequestParam(defaultValue = "1") int page){
        return ApiResponse.<PaginatedResponse<TransactionResponse>>builder()
                .code(1000)
                .result(transactionService.getAll(page))
                .message("Get all transaction in page, " + page)
                .build();
    }

    @GetMapping("/{transactionId}")
    ApiResponse<TransactionDetailResponse> getDetail(@PathVariable("transactionId") String id){
        return ApiResponse.<TransactionDetailResponse>builder()
                .code(1000)
                .result(transactionService.getDetail(id))
                .message("Admin get a transaction detail successfully!")
                .build();
    }

    @DeleteMapping("/{transactionId}")
    ApiResponse<Void> delete(@PathVariable("transactionId") String id){
        transactionService.delete(id);
        return ApiResponse.<Void>builder()
                .code(999)
                .message("Admin deactive a transaction successfully!")
                .build();
    }
}
