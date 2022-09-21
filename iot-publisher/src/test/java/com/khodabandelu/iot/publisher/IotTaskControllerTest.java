package com.khodabandelu.iot.publisher;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.khodabandelu.iot.publisher.domain.BrokerType;
import com.khodabandelu.iot.publisher.domain.IotData;
import com.khodabandelu.iot.publisher.domain.IotTask;
import com.khodabandelu.iot.publisher.service.IotTaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class IotTaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private IotTaskService iotTaskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testListTasksEndpointSuccess() throws Exception {
        var heartRateMeter = IotData.builder().name("heart rate meter").minValue(0).maxValue(120).build();
        var heartRateMeterTask = IotTask.builder().id(UUID.randomUUID().toString()).name("heart rate meter task").brokerType(BrokerType.kafka).data(heartRateMeter).cronExpression("*/1 * * * * *").build();

        var carFuelReadings = IotData.builder().name("car fuel readings").minValue(0).maxValue(50).build();
        var carFuelReadingsTask = IotTask.builder().id(UUID.randomUUID().toString()).name("car fuel readings task").brokerType(BrokerType.kafka).data(carFuelReadings).cronExpression("*/1 * * * * *").build();

        var id = this.iotTaskService.createTask(heartRateMeterTask);

        // Get all the users
        this.mockMvc.perform(get("/api/iot/tasks")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.list").value(hasItem(id)));
    }

    @Test
    public void testCreateTaskEndpointSuccess() throws Exception {
        var heartRateMeter = IotData.builder().name("heart rate meter").minValue(0).maxValue(120).build();
        var heartRateMeterTask = IotTask.builder()
                .id(UUID.randomUUID().toString())
                .name("heart rate meter task")
                .brokerType(BrokerType.kafka)
                .data(heartRateMeter)
                .cronExpression("*/1 * * * * *")
                .build();

        var jsonContent = objectMapper.writeValueAsString(heartRateMeterTask);


        this.mockMvc.perform(post("/api/iot/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andReturn();
    }

    @Test
    public void testDeleteTaskEndpointSuccess() throws Exception {
        var heartRateMeter = IotData.builder().name("heart rate meter").minValue(0).maxValue(120).build();
        var heartRateMeterTask = IotTask.builder().id(UUID.randomUUID().toString()).name("heart rate meter task").brokerType(BrokerType.kafka).data(heartRateMeter).cronExpression("*/1 * * * * *").build();

        var jsonContent = objectMapper.writeValueAsString(heartRateMeterTask);

        var id = this.iotTaskService.createTask(heartRateMeterTask);

        this.mockMvc.perform(delete("/api/iot/tasks/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").isNotEmpty())
                .andReturn();
    }

}
