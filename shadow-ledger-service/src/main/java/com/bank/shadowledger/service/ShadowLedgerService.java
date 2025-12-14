package com.bank.shadowledger.service;

import com.bank.shadowledger.document.LedgerEntry;
import com.bank.shadowledger.repository.LedgerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ShadowLedgerService {

    private final LedgerRepository repository;

    public BigDecimal computeBalance(String accountId) {

        List<LedgerEntry> entries =
                repository.findByAccountIdOrderByTimestampAscEventIdAsc(accountId);

        BigDecimal balance = BigDecimal.ZERO;

        for (LedgerEntry e : entries) {
            if ("credit".equalsIgnoreCase(e.getType())) {
                balance = balance.add(e.getAmount());
            } else {
                balance = balance.subtract(e.getAmount());
                if (balance.compareTo(BigDecimal.ZERO) < 0) {
                    throw new IllegalStateException("Negative balance detected");
                }
            }
        }
        return balance;
    }
}
