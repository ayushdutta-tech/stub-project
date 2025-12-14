package com.bank.shadowledger.dto;

import java.math.BigDecimal;

public record ShadowBalanceResponse(
        String accountId,
        BigDecimal balance
) {}
