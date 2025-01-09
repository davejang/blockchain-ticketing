package com.davejang.blockchain_ticketing.event.domain;

import com.davejang.blockchain_ticketing.member.domain.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jnr.constants.platform.Local;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotBlank
    private String eventName;

    private String description;

    @NotBlank
    private LocalDate startDate;

    @NotBlank
    private LocalDate endDate;

    @Builder
    public Event(String eventName,
                 String description,
                 LocalDate startDate,
                 LocalDate endDate) {
        this.eventName = eventName;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
