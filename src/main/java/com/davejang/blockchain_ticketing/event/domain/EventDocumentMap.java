package com.davejang.blockchain_ticketing.event.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EventDocumentMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "map_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "event_id")
    private Event eventId;

    @Column(name = "event_document_id")
    private String eventDocumentId;

    @Builder
    public EventDocumentMap(Event eventId,
                            String eventDocumentId) {
        this.eventId = eventId;
        this.eventDocumentId = eventDocumentId;
    }
}
