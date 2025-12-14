package com.bank.drift.dto;

import java.math.BigDecimal;

public record CBSBalanceDTO(
        String accountId,
        BigDecimal reportedBalance
) {}
