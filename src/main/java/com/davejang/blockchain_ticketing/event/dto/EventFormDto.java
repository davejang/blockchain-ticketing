package com.davejang.blockchain_ticketing.event.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EventFormDto {
    @NotBlank
    private String eventName;

    private String description;

    @NotBlank
    private String startDate;

    @NotBlank
    private String endDate;
}
