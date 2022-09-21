package com.khodabandelu.iot.collector.service;

import com.khodabandelu.iot.collector.api.queries.FindOneAggregateQuery;
import com.khodabandelu.iot.collector.domain.SensorEvent;

import java.util.List;

public interface SensorEventService {
    void save(SensorEvent event);

    void saveAll(List<SensorEvent> events);

    Double aggregateEvents(FindOneAggregateQuery query);

}
