package com.khodabandelu.iot.collector.service.impl;

import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.select.Selector;
import com.khodabandelu.iot.collector.api.queries.FindOneAggregateQuery;
import com.khodabandelu.iot.collector.domain.SensorEvent;
import com.khodabandelu.iot.collector.repository.SensorEventRepository;
import com.khodabandelu.iot.collector.service.SensorEventService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;

@Service
@RequiredArgsConstructor
public class SensorEventServiceImpl implements SensorEventService {

    private final SensorEventRepository sensorEventRepository;

    private final CassandraOperations cassandraTemplate;

    @Override
    public void save(SensorEvent event) {
        sensorEventRepository.save(event);
    }

    @Override
    public void saveAll(List<SensorEvent> events) {
        sensorEventRepository.saveAll(events);
    }

    @Override
    public Double aggregateEvents(FindOneAggregateQuery query) {
        Double aggregateValue = null;
        switch (query.aggregateType) {
            case max, min, avg -> {
                var aggregateQuery = QueryBuilder.selectFrom("sensorevent")
                        .function(query.getAggregateType().name(), Selector.column("value"))
                        .whereColumn("sensorId").isEqualTo(literal(query.getSensorId()))
                        .whereColumn("groupId").isEqualTo(literal(query.getGroupId()))
                        .whereColumn("timestamp").isGreaterThanOrEqualTo(literal(query.getFromDate()))
                        .whereColumn("timestamp").isLessThanOrEqualTo(literal(query.getToDate()))
                        .build();
                aggregateValue = cassandraTemplate.selectOne(aggregateQuery, Double.class);
            }
            case median -> {
                var listQuery = QueryBuilder.selectFrom("sensorevent")
                        .all()
                        .whereColumn("sensorId").isEqualTo(literal(query.getSensorId()))
                        .whereColumn("groupId").isEqualTo(literal(query.getGroupId()))
                        .whereColumn("timestamp").isGreaterThanOrEqualTo(literal(query.getFromDate()))
                        .whereColumn("timestamp").isLessThanOrEqualTo(literal(query.getToDate()))
                        .build();
                var list = cassandraTemplate.select(listQuery,SensorEvent.class);
                aggregateValue = getMedianValue(list);
            }
        }
        return aggregateValue;
    }

    @NotNull
    private static Double getMedianValue(List<SensorEvent> list) {
        var values = list.stream().map(SensorEvent::getValue).sorted().toList();
        int len = list.size();
        if (len == 0){
            return null;
        }
        if (len == 1){
            return Double.valueOf(values.get(0));
        }
        if (len % 2 == 1) {
            return Double.valueOf(values.get(len / 2));
        } else {
            return 0.5 * (values.get(len / 2) + values.get(len / 2 - 1));
        }
    }
}
