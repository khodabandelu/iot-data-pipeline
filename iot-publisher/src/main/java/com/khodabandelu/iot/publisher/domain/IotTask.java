package com.khodabandelu.iot.publisher.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class IotTask {
    private String id;

    @NotBlank
    private String name;

    private String topic;

    private String groupId;

    @NotNull
    private BrokerType brokerType;

    @NotBlank
    private String cronExpression;

    @Valid
    private IotData data;

}
