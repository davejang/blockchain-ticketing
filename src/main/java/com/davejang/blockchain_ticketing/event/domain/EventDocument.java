package com.davejang.blockchain_ticketing.event.domain;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "events")
@Data
public class EventDocument {

    @Id
    private String id;

    private String eventName;

    private String location;

    @Builder
    public EventDocument(String eventName,
                         String location) {
        this.eventName = eventName;
        this.location = location;
    }
}
