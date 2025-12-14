package com.bank.drift.controller;

import com.bank.drift.dto.CBSBalanceDTO;
import com.bank.drift.dto.DriftResultDTO;
import com.bank.drift.service.DriftCorrectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
public class DriftController {

    private final DriftCorrectionService service;

    @PostMapping("/drift-check")
    public DriftResultDTO driftCheck(@RequestBody CBSBalanceDTO cbs) {
        return service.checkDrift(cbs);
    }

    @PostMapping("/correct/{accountId}")
    public void manualCorrection(
            @PathVariable String accountId,
            @RequestParam BigDecimal amount
    ) {
        service.publishCorrection(accountId, amount);
    }
}
