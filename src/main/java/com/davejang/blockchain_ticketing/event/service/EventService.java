package com.davejang.blockchain_ticketing.event.service;

import com.davejang.blockchain_ticketing.event.domain.Event;
import com.davejang.blockchain_ticketing.event.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class EventService {
    private final EventRepository eventRepository;

    @Autowired
    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> findAllEvents() {
        return eventRepository.findAll();
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

        return eventRepository.save(event);
    }
}
