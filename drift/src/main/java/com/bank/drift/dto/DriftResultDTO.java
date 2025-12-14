package com.bank.drift.dto;

import java.math.BigDecimal;

public record DriftResultDTO(
        String accountId,
        BigDecimal shadowBalance,
        BigDecimal reportedBalance,
        BigDecimal driftAmount,
        String driftType
) {}
