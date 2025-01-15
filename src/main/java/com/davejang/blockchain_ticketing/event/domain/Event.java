package com.davejang.blockchain_ticketing.event.domain;

import com.davejang.blockchain_ticketing.member.domain.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    private String location;

    private String performanceTime;

    @Enumerated(EnumType.STRING)
    private Rating rating;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @Builder
    public Event(String eventName,
                 String description,
                 String location,
                 String performanceTime,
                 Rating rating,
                 LocalDate startDate,
                 LocalDate endDate) {
        this.eventName = eventName;
        this.description = description;
        this.location = location;
        this.performanceTime = performanceTime;
        this.rating = rating;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
