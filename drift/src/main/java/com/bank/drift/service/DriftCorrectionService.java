package com.bank.drift.service;

import com.bank.drift.document.LedgerEntry;
import com.bank.drift.dto.CBSBalanceDTO;
import com.bank.drift.dto.CorrectionEventDTO;
import com.bank.drift.dto.DriftResultDTO;
import com.bank.drift.repository.LedgerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriftCorrectionService {

    private final LedgerRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper mapper = new ObjectMapper();

    public DriftResultDTO checkDrift(CBSBalanceDTO cbs) {

        List<LedgerEntry> entries =
                repository.findByAccountIdOrderByTimestampAscEventIdAsc(cbs.accountId());

        BigDecimal shadowBalance = BigDecimal.ZERO;

        for (LedgerEntry e : entries) {
            if ("credit".equalsIgnoreCase(e.getType())) {
                shadowBalance = shadowBalance.add(e.getAmount());
            } else {
                shadowBalance = shadowBalance.subtract(e.getAmount());
            }
        }

        BigDecimal drift = cbs.reportedBalance().subtract(shadowBalance);

        String driftType =
                drift.compareTo(BigDecimal.ZERO) == 0 ? "NO_DRIFT"
                        : drift.compareTo(BigDecimal.ZERO) > 0 ? "MISSING_CREDIT"
                        : "INCORRECT_DEBIT";

        if (!"NO_DRIFT".equals(driftType)) {
            publishCorrection(cbs.accountId(), drift.abs());
        }

        return new DriftResultDTO(
                cbs.accountId(),
                shadowBalance,
                cbs.reportedBalance(),
                drift,
                driftType
        );
    }

    public void publishCorrection(String accountId, BigDecimal amount) {

        CorrectionEventDTO event = new CorrectionEventDTO(
                "CORR-" + accountId + "-" + UUID.randomUUID(),
                accountId,
                "credit",
                amount
        );

        try {
            kafkaTemplate.send(
                    "transactions.corrections",
                    accountId,
                    mapper.writeValueAsString(event)
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
