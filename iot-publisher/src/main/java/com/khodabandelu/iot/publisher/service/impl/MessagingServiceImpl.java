package com.khodabandelu.iot.publisher.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.khodabandelu.iot.publisher.domain.IotTask;
import com.khodabandelu.iot.publisher.domain.SensorEvent;
import com.khodabandelu.iot.publisher.service.MessagingService;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Consumer;

@Service
@RequiredArgsConstructor
public class MessagingServiceImpl implements MessagingService {

    private final IMqttClient mqttClient;
    private final StreamBridge streamBridge;
    private final ObjectMapper objectMapper;

    @Override
    public void publish(IotTask task) throws MqttException, JsonProcessingException {
        var event = new SensorEvent();
        event.setName(task.getData().getName());
        event.setSensorId(UUID.fromString(task.getId()).toString());
        event.setGroupId(task.getGroupId() == null ? UUID.randomUUID().toString() : task.getGroupId());
        event.setRandomValue(task.getData().getMinValue(), task.getData().getMaxValue());
        var json = objectMapper.writeValueAsBytes(event);

        switch (task.getBrokerType()) {
            case mqtt -> {
                MqttMessage mqttMessage = new MqttMessage();
                mqttMessage.setPayload(json);
                mqttClient.publish(task.getTopic(), mqttMessage);
            }
            case kafka -> streamBridge.send("producer-out-0", event);
        }
    }

    public void subscribeFromMqtt(final String topic) throws MqttException, InterruptedException {
        System.out.println("Messages received:");

        mqttClient.subscribeWithResponse(topic, (tpic, msg) -> {
            System.out.println(msg.getId() + " -> " + new String(msg.getPayload()));
        });
    }

//    @Bean
//    public Supplier<Message> producer() {
//        return () -> new SensorEvent(" new event");
//    }

    @Bean
    public Consumer<SensorEvent> consumer() {
        return message -> System.out.println("received " + message.getSensorId() + "  " + message.getName() + "  time:" + message.getTimestamp() + " : " + message.getValue());
    }

}
