package com.bank.drift.repository;

import com.bank.drift.document.LedgerEntry;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LedgerRepository extends MongoRepository<LedgerEntry, String> {

    List<LedgerEntry> findByAccountIdOrderByTimestampAscEventIdAsc(String accountId);
}
