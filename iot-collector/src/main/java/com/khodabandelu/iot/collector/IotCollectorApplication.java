package com.khodabandelu.iot.collector;

import com.khodabandelu.iot.collector.domain.SensorEvent;
import com.khodabandelu.iot.collector.dto.SensorEventDto;
import com.khodabandelu.iot.collector.mapper.SensorEventMapper;
import com.khodabandelu.iot.collector.service.SensorEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;

import java.util.UUID;
import java.util.function.Consumer;

@SpringBootApplication
@RequiredArgsConstructor
public class IotCollectorApplication {

    private final SensorEventService sensorEventService;

    public static void main(String[] args) {
        SpringApplication.run(IotCollectorApplication.class, args);
    }

    @Bean
    public Consumer<Message> consumer() {
        return message -> {
            SensorEvent event= SensorEventMapper.toEntity((SensorEventDto) message.getPayload());
            event.setTopic(message.getHeaders().get("kafka_receivedTopic").toString());
            sensorEventService.save(event);
            System.out.println("received " + event.getId() + "  " + event.getName() + "  time:" + event.getId().getTimestamp() + " : " + event.getValue());
        };
    }

}
