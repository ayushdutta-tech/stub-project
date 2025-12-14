package com.bank.eventservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class EventRequest {

    @NotBlank
    private String eventId;

    @NotBlank
    private String accountId;

    @NotBlank
    private String type;   // credit | debit

    @NotNull
    @Positive
    private BigDecimal amount;

    @NotNull
    private Long timestamp;
}
