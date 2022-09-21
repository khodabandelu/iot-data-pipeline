package com.khodabandelu.iot.collector.api.queries;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class FindOneAggregateQuery {
    public AggregateType aggregateType;
    private String sensorId;
    private String groupId;
    private long fromDate;
    private long toDate;
}
