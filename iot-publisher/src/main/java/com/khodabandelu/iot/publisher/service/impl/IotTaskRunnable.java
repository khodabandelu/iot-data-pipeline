package com.khodabandelu.iot.publisher.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.khodabandelu.iot.publisher.domain.IotTask;
import com.khodabandelu.iot.publisher.service.MessagingService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IotTaskRunnable implements Runnable {

    @Getter
    @Setter
    private IotTask iotTask;
    private final MessagingService messagingService;

    @Override
    public void run() {
        try {
            messagingService.publish(iotTask);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}

