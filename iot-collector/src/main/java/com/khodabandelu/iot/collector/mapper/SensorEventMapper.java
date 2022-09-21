package com.khodabandelu.iot.collector.mapper;

import com.khodabandelu.iot.collector.domain.SensorEvent;
import com.khodabandelu.iot.collector.domain.SensorEventKey;
import com.khodabandelu.iot.collector.dto.SensorEventDto;

public class SensorEventMapper {

    public static SensorEvent toEntity(SensorEventDto dto) {
        return SensorEvent.builder()
                .id(SensorEventKey.builder()
                        .sensorId(dto.getSensorId())
                        .groupId(dto.getGroupId())
                        .timestamp(dto.getTimestamp().getTime())
                        .build())
                .name(dto.getName())
                .value(dto.getValue())
                .build();

    }

}
