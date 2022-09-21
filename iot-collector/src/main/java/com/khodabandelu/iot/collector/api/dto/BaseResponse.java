package com.khodabandelu.iot.collector.api.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BaseResponse {
    private String id;
    private String message;

    public BaseResponse(String message) {
        this.message = message;
    }
}
