package com.bank.shadowledger.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document(collection = "ledger")
public class LedgerEntry {

    @Id
    private String id;

    @Indexed(unique = true)
    private String eventId;

    private String accountId;
    private String type; // debit / credit
    private BigDecimal amount;
    private Long timestamp;
}
