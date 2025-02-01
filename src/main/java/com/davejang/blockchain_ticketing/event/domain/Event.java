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
@Table(name = "event")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    private String posterUrl;

    private String deployAddress;

    @NotBlank
    private String eventName;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Genre genre;

    private String description;

    @NotBlank
    private String location;

    @NotBlank
    private String performanceTime;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Rating rating;

    @NotNull
    private int price;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotBlank
    private String documentId;

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
                 LocalDate endDate,
                 String documentId) {
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
        this.documentId = documentId;
    }

}
