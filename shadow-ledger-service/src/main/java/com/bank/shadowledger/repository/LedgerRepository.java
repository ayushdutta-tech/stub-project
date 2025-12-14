package com.bank.shadowledger.repository;

import com.bank.shadowledger.document.LedgerEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LedgerRepository extends MongoRepository<LedgerEntry, String> {

    boolean existsByEventId(String eventId);

    List<LedgerEntry> findByAccountIdOrderByTimestampAscEventIdAsc(String accountId);
}
