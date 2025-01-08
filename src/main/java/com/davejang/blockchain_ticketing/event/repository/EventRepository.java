package com.davejang.blockchain_ticketing.event.repository;

import com.davejang.blockchain_ticketing.event.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByEventName(String eventName);
}
