package com.davejang.blockchain_ticketing.event.service;

import com.davejang.blockchain_ticketing.event.domain.Event;
import com.davejang.blockchain_ticketing.event.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
public class EventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Transactional
    public Event registerEvent(String eventName,
                               String description,
                               LocalDateTime startDate,
                               LocalDateTime endDate) {

        Event event = Event.builder()
                .eventName(eventName)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        return event;
    }
}
