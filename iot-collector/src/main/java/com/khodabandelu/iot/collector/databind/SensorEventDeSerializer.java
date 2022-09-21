package com.khodabandelu.iot.collector.databind;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.khodabandelu.iot.collector.domain.SensorEvent;
import com.khodabandelu.iot.collector.dto.SensorEventDto;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class SensorEventDeSerializer implements Deserializer<SensorEventDto> {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Override
    public SensorEventDto deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(new String(data), SensorEventDto.class);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }
}