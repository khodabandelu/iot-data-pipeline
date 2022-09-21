package com.khodabandelu.iot.collector.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import java.io.Serializable;

@PrimaryKeyClass
@Data
@EqualsAndHashCode
@Builder
public class SensorEventKey implements Serializable {

    @PrimaryKeyColumn(name = "sensorid", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String sensorId;

    @PrimaryKeyColumn(name = "timestamp", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private long timestamp;

    @PrimaryKeyColumn(name = "groupid", ordinal = 2, type = PrimaryKeyType.PARTITIONED)
    private String groupId;

}