package com.khodabandelu.iot.publisher.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class ListTaskResponse extends BaseResponse {
    private List<String> list;

    public ListTaskResponse(String message, List<String> list) {
        super(message);
        this.list = list;
    }

    public ListTaskResponse(String id, String message, List<String> list) {
        super(id, message);
        this.list = list;
    }
}
