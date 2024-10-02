package com.trongdev.banking_system.controller;

import com.trongdev.banking_system.dto.request.BankCreateRequest;
import com.trongdev.banking_system.dto.request.BankUpdateRequest;
import com.trongdev.banking_system.dto.response.ApiResponse;
import com.trongdev.banking_system.dto.response.BankResponse;
import com.trongdev.banking_system.dto.response.PaginatedResponse;
import com.trongdev.banking_system.service.BankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/banks")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Bank Controller")
public class BankController {
    BankService bankService;

    @Operation(summary = "Add new bank", description = "Send a request via this API to create new bank")
    @PostMapping
    ApiResponse<BankResponse> create(@RequestBody BankCreateRequest request){
        return ApiResponse.<BankResponse>builder()
                .code(2000)
                .result(bankService.create(request))
                .message("Admin create a bank successfully!")
                .build();
    }

    @Operation(summary = "Get list of banks", description = "Send a request via this API to get bank list by page")
    @GetMapping
    ApiResponse<PaginatedResponse<BankResponse>> getAll(@RequestParam(defaultValue = "1") int page){
        return ApiResponse.<PaginatedResponse<BankResponse>>builder()
                .code(1000)
                .result(bankService.getAll(page))
                .message("Success get all banks with page" + page)
                .build();
    }

    @Operation(summary = "Get bank detail", description = "Send a request via this API to get bank information")
    @GetMapping("/{bankId}")
    ApiResponse<BankResponse> getDetail(@PathVariable("bankId") int id){
        return ApiResponse.<BankResponse>builder()
                .code(1000)
                .result(bankService.getDetail(id))
                .message("Success get bank detail")
                .build();
    }

    @Operation(summary = "Update bank", description = "Send a request via this API to update a bank")
    @PutMapping("/{bankId}")
    ApiResponse<BankResponse> update(@PathVariable("bankId") int id, @RequestBody BankUpdateRequest request){
        return ApiResponse.<BankResponse>builder()
                .code(2000)
                .result(bankService.update(id, request))
                .message("Admin update a bank successfully!")
                .build();
    }

    @Operation(summary = "Delete bank", description = "Send a request via this API to remove a bank")
    @DeleteMapping("/{bankId}")
    ApiResponse<Void> delete(@PathVariable("bankId") int id){
        bankService.delete(id);
        return ApiResponse.<Void>builder()
                .code(999)
                .message("Bank deleted by admin successfully!")
                .build();
    }
}
