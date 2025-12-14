package com.bank.eventservice.repository;

import com.bank.eventservice.document.EventDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EventRepository extends MongoRepository<EventDocument, String> {
    boolean existsByEventId(String eventId);
}
