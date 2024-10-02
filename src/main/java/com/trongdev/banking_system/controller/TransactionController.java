package com.trongdev.banking_system.controller;

import com.trongdev.banking_system.dto.request.TransactionRequest;
import com.trongdev.banking_system.dto.response.ApiResponse;
import com.trongdev.banking_system.dto.response.PaginatedResponse;
import com.trongdev.banking_system.dto.response.TransactionDetailResponse;
import com.trongdev.banking_system.dto.response.TransactionResponse;
import com.trongdev.banking_system.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Transaction Controller")
public class TransactionController {
    TransactionService transactionService;

    @Operation(summary = "Create transaction", description = "Send a request via this API to create a transaction")
    @PostMapping
    ApiResponse<TransactionResponse> create(@RequestBody TransactionRequest request){
        return ApiResponse.<TransactionResponse>builder()
                .code(2000)
                .result(transactionService.create(request))
                .message("Transaction execute successfully!")
                .build();
    }

    @Operation(summary = "Get a list of transaction",
            description = "Send a request via this API to get transactions list by page")
    @GetMapping
    ApiResponse<PaginatedResponse<TransactionResponse>> getAll(@RequestParam(defaultValue = "1") int page){
        return ApiResponse.<PaginatedResponse<TransactionResponse>>builder()
                .code(1000)
                .result(transactionService.getAll(page))
                .message("Get all transaction in page, " + page)
                .build();
    }

    @Operation(summary = "Get transaction detail",
            description = "Send a request via this API to get transaction information")
    @GetMapping("/{transactionId}")
    ApiResponse<TransactionDetailResponse> getDetail(@PathVariable("transactionId") String id){
        return ApiResponse.<TransactionDetailResponse>builder()
                .code(1000)
                .result(transactionService.getDetail(id))
                .message("Admin get a transaction detail successfully!")
                .build();
    }

    @Operation(summary = "Delete transaction", description = "Send a request via this API to hide a transaction")
    @DeleteMapping("/{transactionId}")
    ApiResponse<Void> delete(@PathVariable("transactionId") String id){
        transactionService.delete(id);
        return ApiResponse.<Void>builder()
                .code(999)
                .message("Admin deactive a transaction successfully!")
                .build();
    }
}
