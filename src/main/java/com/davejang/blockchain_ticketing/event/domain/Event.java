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
import org.springframework.data.elasticsearch.annotations.Document;

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

    private String posterUrl;

    private String deployAddress;

    @NotBlank
    private String eventName;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    private String description;

    private String location;

    private String performanceTime;

    @Enumerated(EnumType.STRING)
    private Rating rating;

    @NotNull
    private int price;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @Builder
    public Event(String posterUrl,
                 String eventName,
                 Genre genre,
                 String description,
                 String location,
                 String performanceTime,
                 Rating rating,
                 int price,
                 LocalDate startDate,
                 LocalDate endDate) {
        this.posterUrl = posterUrl;
        this.eventName = eventName;
        this.genre = genre;
        this.description = description;
        this.location = location;
        this.performanceTime = performanceTime;
        this.rating = rating;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
    }

}
