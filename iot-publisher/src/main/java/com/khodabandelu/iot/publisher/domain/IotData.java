package com.khodabandelu.iot.publisher.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IotData {
    @NotBlank
    private String name;

    @PositiveOrZero
    private long minValue;

    @Positive
    private long maxValue;
}
