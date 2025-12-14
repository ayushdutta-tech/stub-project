package com.bank.shadowledger.consumer;

import com.bank.shadowledger.document.LedgerEntry;
import com.bank.shadowledger.repository.LedgerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TransactionConsumer {

    private final LedgerRepository repository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = {"transactions.raw", "transactions.corrections"})
    public void consume(String message) throws Exception {

        LedgerEntry entry = objectMapper.readValue(message, LedgerEntry.class);

        // ✅ Explicit log to verify Kafka consumption
        System.out.println("Consumed eventId: " + entry.getEventId());

        // ✅ Deduplication check
        if (repository.existsByEventId(entry.getEventId())) {
            System.out.println("Duplicate event ignored: " + entry.getEventId());
            return;
        }

        // ✅ Persist immutable ledger entry
        repository.save(entry);
        System.out.println("Ledger entry saved for eventId: " + entry.getEventId());
    }
}
