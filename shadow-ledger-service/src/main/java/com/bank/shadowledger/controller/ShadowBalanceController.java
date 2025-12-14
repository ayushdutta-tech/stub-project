package com.bank.shadowledger.controller;

import com.bank.shadowledger.dto.ShadowBalanceResponse;
import com.bank.shadowledger.service.ShadowLedgerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class ShadowBalanceController {

    private final ShadowLedgerService service;

    @GetMapping("/{accountId}/shadow-balance")
    public ShadowBalanceResponse getBalance(@PathVariable String accountId) {

        BigDecimal balance = service.computeBalance(accountId);
        return new ShadowBalanceResponse(accountId, balance);
    }
}
