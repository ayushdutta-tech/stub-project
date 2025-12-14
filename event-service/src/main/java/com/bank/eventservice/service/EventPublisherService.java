package com.bank.eventservice.service;

import com.bank.eventservice.document.EventDocument;
import com.bank.eventservice.dto.EventRequest;
import com.bank.eventservice.exception.DuplicateEventException;
import com.bank.eventservice.repository.EventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventPublisherService {

    private final EventRepository repository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String TOPIC = "transactions.raw";

    public void publish(EventRequest request) throws Exception {

        if (repository.existsByEventId(request.getEventId())) {
            throw new DuplicateEventException(
                    "Duplicate eventId: " + request.getEventId()
            );
        }

        // ✅ USE EventDocument (Mongo)
        EventDocument document = new EventDocument();
        document.setEventId(request.getEventId());
        document.setAccountId(request.getAccountId());
        document.setType(request.getType());
        document.setAmount(request.getAmount());
        document.setTimestamp(request.getTimestamp());

        repository.save(document);

        kafkaTemplate.send(
                TOPIC,
                request.getEventId(),
                objectMapper.writeValueAsString(request)
        );
    }
}
