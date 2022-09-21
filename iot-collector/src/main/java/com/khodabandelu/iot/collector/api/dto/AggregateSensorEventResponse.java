package com.khodabandelu.iot.collector.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class AggregateSensorEventResponse extends BaseResponse {
    private Double aggregateValue;

    public AggregateSensorEventResponse(String message, Double aggregateValue) {
        super(message);
        this.aggregateValue = aggregateValue;
    }
}
