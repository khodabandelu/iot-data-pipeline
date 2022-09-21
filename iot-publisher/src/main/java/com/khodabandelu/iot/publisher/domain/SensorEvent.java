package com.khodabandelu.iot.publisher.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Random;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class SensorEvent {

    @NotNull
    private String sensorId;

    private String groupId;

    @NotNull
    private String name;
    @NotNull
    private long value;

    @NotNull
    private Timestamp timestamp = Timestamp.from(Instant.now());

    public void setRandomValue(long minValue, long maxValue) {
        this.value = new Random().nextLong(minValue, maxValue);
    }

}
