package com.davejang.blockchain_ticketing.event.service;

import com.davejang.blockchain_ticketing.event.domain.Event;
import com.davejang.blockchain_ticketing.event.repository.EventRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private EventService eventService;

    @Test
    void registerEvent() {

        LocalDate date = LocalDate.now();
//
//        Event validateEvent = eventService.registerEvent("test","test", "R18" , date, date);
//
//        assertThat(validateEvent.getEventName()).isEqualTo("test");
//        assertThat(validateEvent.getDescription()).isEqualTo("test");
//        assertThat(validateEvent.getStartDate()).isEqualTo(date);
//        assertThat(validateEvent.getEndDate()).isEqualTo(date);
    }
}