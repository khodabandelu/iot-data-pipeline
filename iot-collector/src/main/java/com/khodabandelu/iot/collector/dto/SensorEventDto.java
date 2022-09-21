package com.khodabandelu.iot.collector.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Table;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SensorEventDto {

    private String sensorId;

    private String groupId;

    private String name;

    private long value;

    private Timestamp timestamp;

}
