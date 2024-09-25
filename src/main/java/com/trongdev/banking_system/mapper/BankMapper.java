package com.trongdev.banking_system.mapper;

import com.trongdev.banking_system.dto.request.BankCreateRequest;
import com.trongdev.banking_system.dto.request.BankUpdateRequest;
import com.trongdev.banking_system.dto.response.BankResponse;
import com.trongdev.banking_system.entity.Bank;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BankMapper {
    Bank toBank(BankCreateRequest request);
    BankResponse toBankResponse(Bank bank);

    void updateBank(@MappingTarget Bank bank, BankUpdateRequest request);
}
