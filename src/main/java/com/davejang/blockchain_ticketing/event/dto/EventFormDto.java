package com.davejang.blockchain_ticketing.event.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventFormDto {
    @NotBlank
    private String eventName;

    private String description;

    @NotBlank
    private LocalDateTime startDate;

    @NotBlank
    private LocalDateTime endDate;
}
