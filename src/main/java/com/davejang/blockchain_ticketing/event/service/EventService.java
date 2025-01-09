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
                               String startDate,
                               String endDate) {

        LocalDate startDateParse = LocalDate.parse(startDate);
        LocalDate endDateParse = LocalDate.parse(endDate);

        if(eventName.isEmpty()) {
            log.error("이벤트 제목이 공백입니다");
            throw new IllegalArgumentException("이벤트 제목을 작성하세요");
        }
        if(startDate.isEmpty() || endDate.isEmpty()) {
            log.error("날짜가 공백입니다");
            throw new IllegalArgumentException("날짜를 입력하세요");
        }
        if(startDateParse.isAfter(endDateParse)) {
            log.info("시작일 {} 이 종료일 {} 이후입니다.", startDate, endDate);
            throw new IllegalArgumentException("날짜를 확인하세요");
        }

        Event event = Event.builder()
                .eventName(eventName)
                .description(description)
                .startDate(startDateParse)
                .endDate(endDateParse)
                .build();

        return eventRepository.save(event);
    }
}
