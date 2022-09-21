package com.khodabandelu.iot.publisher.databind;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.khodabandelu.iot.publisher.domain.SensorEvent;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.Deserializer;

import java.io.IOException;

public class SensorEventDeSerializer implements Deserializer<SensorEvent> {

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Override
    public SensorEvent deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(new String(data), SensorEvent.class);
        } catch (IOException e) {
            throw new SerializationException(e);
        }
    }
}