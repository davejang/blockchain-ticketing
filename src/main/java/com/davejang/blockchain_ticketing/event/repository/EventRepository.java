package com.davejang.blockchain_ticketing.event.repository;

import com.davejang.blockchain_ticketing.event.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByEventName(String eventName);
}
