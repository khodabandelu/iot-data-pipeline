package com.khodabandelu.iot.collector.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import javax.validation.constraints.NotNull;

@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SensorEvent extends BaseEvent {

    @PrimaryKey()
    @NotNull
    private SensorEventKey id;

    @NotNull
    private String name;

    @NotNull
    private long value;

    @NotNull
    private String topic;

}
