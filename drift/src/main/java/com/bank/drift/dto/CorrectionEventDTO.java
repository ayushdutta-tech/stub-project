package com.bank.drift.dto;

import java.math.BigDecimal;

public record CorrectionEventDTO(
        String eventId,
        String accountId,
        String type,
        BigDecimal amount
) {}
