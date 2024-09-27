package com.trongdev.banking_system.helper;

import com.trongdev.banking_system.entity.Bank;
import com.trongdev.banking_system.entity.BankRule;
import com.trongdev.banking_system.exception.ErrorCode;
import com.trongdev.banking_system.repository.BankRuleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Random;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class AccountNumberGenerator {
    final BankRuleRepository bankRuleRepository;

    public String generateAccountNumber(Bank bank) {
        BankRule bankRule = bankRuleRepository.findByBank(bank)
                .orElseThrow(() -> new RuntimeException(ErrorCode.BANK_NOT_EXISTED.getMessage()));

        String prefix = bankRule.getPrefix();
        int length = bankRule.getAccountNumberLength();

        return prefix + generateRandomNumber(length - prefix.length());
    }

    private static String generateRandomNumber(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10)); // Sinh số ngẫu nhiên từ 0-9
        }

        return sb.toString();
    }
}
