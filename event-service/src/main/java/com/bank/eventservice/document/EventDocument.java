package com.bank.eventservice.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Document(collection = "events")
public class EventDocument {

    @Id
    private String id;

    @Indexed(unique = true)
    private String eventId;

    private String accountId;
    private String type;
    private BigDecimal amount;
    private Long timestamp;
}
