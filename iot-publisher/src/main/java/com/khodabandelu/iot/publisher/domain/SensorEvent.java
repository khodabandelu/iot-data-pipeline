package com.khodabandelu.iot.publisher.domain;

import com.khodabandelu.iot.publisher.util.NumberUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.Instant;

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
        this.value = NumberUtils.randomValue(minValue, maxValue);
    }

}
