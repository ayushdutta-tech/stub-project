package com.bank.eventservice.controller;

import com.bank.eventservice.dto.EventRequest;
import com.bank.eventservice.service.EventPublisherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
@RequiredArgsConstructor
public class EventController {

    private final EventPublisherService service;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void createEvent(@Valid @RequestBody EventRequest request)
            throws Exception {
        service.publish(request);
    }
}
