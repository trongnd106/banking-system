package com.trongdev.banking_system.service;

import com.trongdev.banking_system.dto.request.BankCreateRequest;
import com.trongdev.banking_system.dto.request.BankUpdateRequest;
import com.trongdev.banking_system.dto.response.BankResponse;
import com.trongdev.banking_system.dto.response.PaginatedResponse;
import com.trongdev.banking_system.entity.Bank;
import com.trongdev.banking_system.exception.AppException;
import com.trongdev.banking_system.exception.ErrorCode;
import com.trongdev.banking_system.mapper.BankMapper;
import com.trongdev.banking_system.repository.BankRepository;
import com.trongdev.banking_system.utils.ConstValue;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BankService {
    BankRepository bankRepository;
    BankMapper bankMapper;
    @PreAuthorize("hasAuthority('CREATE_BANK')")
    public BankResponse create(BankCreateRequest request){
        Bank bank = bankMapper.toBank(request);
        return bankMapper.toBankResponse(bankRepository.save(bank));
    }

    @PreAuthorize("hasAuthority('UPDATE_BANK')")
    public BankResponse update(int id, BankUpdateRequest request){
        Bank bank = bankRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.BANK_NOT_EXISTED)
        );
        bankMapper.updateBank(bank,request);
        return bankMapper.toBankResponse(bankRepository.save(bank));
    }

    @PreAuthorize("hasAuthority('VIEW_BANK_LIST')")
    public PaginatedResponse<BankResponse> getAll(int page){
        var perPage = ConstValue.PER_PAGE;
        Pageable pageable = PageRequest.of(page - 1, perPage);
        Page<Bank> bankPage = bankRepository.findAll(pageable);
        List<BankResponse> bankResponses = bankPage.getContent().stream()
                .map(bankMapper::toBankResponse).toList();

        int totalPage = bankPage.getTotalPages();
        int nextPage = page < totalPage ? page + 1 : 0;
        int prevPage = page > 1 ? page - 1 : 0;

        return PaginatedResponse.<BankResponse>builder()
                .totalPage(totalPage)
                .perPage(perPage)
                .curPage(page)
                .nextPage(nextPage)
                .prevPage(prevPage)
                .data(bankResponses)
                .build();
    }

    @PreAuthorize("hasAuthority('VIEW_BANK_DETAIL')")
    public BankResponse getDetail(int id){
        Bank bank = bankRepository.findById(id).orElseThrow(
                () -> new AppException(ErrorCode.BANK_NOT_EXISTED)
        );
        return bankMapper.toBankResponse(bank);
    }

    @PreAuthorize("hasAuthority('DELETE_BANK')")
    public void delete(int id){
        bankRepository.deleteById(id);
    }
}
