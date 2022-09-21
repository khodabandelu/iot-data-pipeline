package com.khodabandelu.iot.publisher.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.khodabandelu.iot.publisher.domain.IotTask;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

public interface MessagingService {

    void publish(IotTask task) throws MqttPersistenceException, MqttException, JsonProcessingException;

    void subscribeFromMqtt(String topic) throws MqttException, InterruptedException;
}
