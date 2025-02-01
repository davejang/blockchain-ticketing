package com.davejang.blockchain_ticketing.event.repository;

import com.davejang.blockchain_ticketing.event.domain.EventDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface EventSearchRepository extends ElasticsearchRepository<EventDocument, String> {
    List<EventDocument> findByEventNameContaining(String keyword);
}
