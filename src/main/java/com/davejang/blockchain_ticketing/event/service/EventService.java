package com.davejang.blockchain_ticketing.event.service;

import com.davejang.blockchain_ticketing.event.domain.Event;
import com.davejang.blockchain_ticketing.event.repository.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
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
                               LocalDate startDate,
                               LocalDate endDate) {

        if(startDate.isAfter(endDate)) {
            log.info("시작일 {} 이 종료일 {} 이후입니다.", startDate, endDate);
            throw new IllegalArgumentException("날짜 설정 에러");
        }

        Event event = Event.builder()
                .eventName(eventName)
                .description(description)
                .startDate(startDate)
                .endDate(endDate)
                .build();

        return eventRepository.save(event);
    }
}
