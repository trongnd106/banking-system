package com.trongdev.banking_system.controller;

import com.trongdev.banking_system.dto.request.BankCreateRequest;
import com.trongdev.banking_system.dto.request.BankUpdateRequest;
import com.trongdev.banking_system.dto.response.ApiResponse;
import com.trongdev.banking_system.dto.response.BankResponse;
import com.trongdev.banking_system.dto.response.PaginatedResponse;
import com.trongdev.banking_system.service.BankService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/banks")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BankController {
    BankService bankService;

    @PostMapping
    ApiResponse<BankResponse> create(@RequestBody BankCreateRequest request){
        return ApiResponse.<BankResponse>builder()
                .code(2000)
                .result(bankService.create(request))
                .message("Admin create a bank successfully!")
                .build();
    }

    @GetMapping
    ApiResponse<PaginatedResponse<BankResponse>> getAll(@RequestParam(defaultValue = "1") int page){
        return ApiResponse.<PaginatedResponse<BankResponse>>builder()
                .code(1000)
                .result(bankService.getAll(page))
                .message("Success get all banks with page" + page)
                .build();
    }

    @GetMapping("/{bankId}")
    ApiResponse<BankResponse> getDetail(@PathVariable("bankId") int id){
        return ApiResponse.<BankResponse>builder()
                .code(1000)
                .result(bankService.getDetail(id))
                .message("Success get bank detail")
                .build();
    }

    @PutMapping("/{bankId}")
    ApiResponse<BankResponse> update(@PathVariable("bankId") int id, @RequestBody BankUpdateRequest request){
        return ApiResponse.<BankResponse>builder()
                .code(2000)
                .result(bankService.update(id, request))
                .message("Admin update a bank successfully!")
                .build();
    }

    @DeleteMapping("/{bankId}")
    ApiResponse<Void> delete(@PathVariable("bankId") int id){
        bankService.delete(id);
        return ApiResponse.<Void>builder()
                .code(999)
                .message("Bank deleted by admin successfully!")
                .build();
    }
}
