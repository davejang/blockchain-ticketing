package com.davejang.blockchain_ticketing.event.service;

import com.davejang.blockchain_ticketing.event.domain.Event;
import com.davejang.blockchain_ticketing.event.domain.EventDocument;
import com.davejang.blockchain_ticketing.event.domain.Genre;
import com.davejang.blockchain_ticketing.event.domain.Rating;
import com.davejang.blockchain_ticketing.event.repository.EventRepository;
import com.davejang.blockchain_ticketing.event.repository.EventSearchRepository;
import com.davejang.blockchain_ticketing.member.domain.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class EventService {
    private final EventRepository eventRepository;
    private final EventSearchRepository eventSearchRepository;

    @Autowired
    public EventService(EventRepository eventRepository,
                        EventSearchRepository eventSearchRepository) {
        this.eventRepository = eventRepository;
        this.eventSearchRepository = eventSearchRepository;
    }

    public List<Event> findAllEvents() {
        return eventRepository.findAll();
    }

    public Event getEvent(Long id) {
        Optional<Event> event = eventRepository.findById(id);
        try {
            return event.get();
        } catch (Exception e) {
            throw new NullPointerException();
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public Event registerEvent(String imagePath,
                               String eventName,
                               String genre,
                               String description,
                               String location,
                               String performanceTime,
                               String rating,
                               int price,
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

        EventDocument eventDocument = EventDocument.builder()
                .eventName(eventName)
                .location(location)
                .build();

        String documentId = eventSearchRepository.save(eventDocument).getId();

        Event event = Event.builder()
                .posterUrl(imagePath)
                .eventName(eventName)
                .genre(Genre.valueOf(genre))
                .description(description)
                .location(location)
                .performanceTime(performanceTime)
                .rating(Rating.valueOf(rating))
                .price(price)
                .startDate(startDateParse)
                .endDate(endDateParse)
                .documentId(documentId)
                .build();

        eventRepository.save(event);

        return event;
    }

    @Transactional
    public void deleteEvent(String eventName) {
        Optional<Event> event = eventRepository.findByEventName(eventName);

        if (event.isEmpty()) {
            log.info("이벤트가 존재하지 않습니다. {}", eventName);
            throw new IllegalArgumentException("존재하지 않는 이벤트입니다");
        }

        eventSearchRepository.deleteById(event.get().getDocumentId());
        eventRepository.delete(event.get());
    }

    public List<EventDocument> searchEvents(String keyword) {
        try {
            List<EventDocument> eventList = eventSearchRepository.findByEventNameContaining(keyword);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return eventSearchRepository.findByEventNameContaining(keyword);
    }
}
