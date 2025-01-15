package com.davejang.blockchain_ticketing.event.dto;

import lombok.Data;

@Data
public class EventFormDto {

    private String eventName;

    private String description;

    private String location;

    private String performanceTime;

    private String rating;

    private int price;

    private String startDate;

    private String endDate;
}
