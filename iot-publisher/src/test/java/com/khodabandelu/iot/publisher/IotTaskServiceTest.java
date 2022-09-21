package com.khodabandelu.iot.publisher;

import com.khodabandelu.iot.publisher.domain.BrokerType;
import com.khodabandelu.iot.publisher.domain.IotData;
import com.khodabandelu.iot.publisher.domain.IotTask;
import com.khodabandelu.iot.publisher.service.IotTaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.*;

@SpringBootTest
public class IotTaskServiceTest {

    @Autowired
    private IotTaskService iotTaskService;

    @Test
    public void testCreateTaskSuccess() throws Exception {
        var heartRateMeter = IotData.builder().name("heart rate meter").minValue(0).maxValue(120).build();
        var heartRateMeterTask = IotTask.builder().id(UUID.randomUUID().toString())
                .name("heart rate meter task")
                .data(heartRateMeter)
                .brokerType(BrokerType.kafka)
                .cronExpression("*/1 * * * * *")
                .build();

        var id = this.iotTaskService.createTask(heartRateMeterTask);
        assertThat(id).isNotNull();
    }

    @Test
    public void testRemoveScheduledTaskSuccess() throws Exception {
        var heartRateMeter = IotData.builder().name("heart rate meter").minValue(0).maxValue(120).build();
        var heartRateMeterTask = IotTask.builder()
                .id(UUID.randomUUID().toString())
                .name("heart rate meter task")
                .data(heartRateMeter)
                .brokerType(BrokerType.kafka)
                .cronExpression("*/1 * * * * *")
                .build();
        var id = this.iotTaskService.createTask(heartRateMeterTask);
        this.iotTaskService.removeScheduledTask(id);
        List<String> tasks = this.iotTaskService.listTasks();
        org.hamcrest.MatcherAssert.assertThat(tasks, hasItem(id));
    }

    @Test
    public void testlistTaskSuccess() throws Exception {
        var heartRateMeter = IotData.builder().name("heart rate meter").minValue(0).maxValue(120).build();
        var heartRateMeterTask = IotTask.builder()
                .id(UUID.randomUUID().toString())
                .name("heart rate meter task")
                .data(heartRateMeter)
                .brokerType(BrokerType.kafka)
                .cronExpression("*/1 * * * * *")
                .build();

        var carFuelReadings = IotData.builder().name("car fuel readings").minValue(0).maxValue(50).build();
        var carFuelReadingsTask = IotTask.builder()
                .id(UUID.randomUUID().toString())
                .name("car fuel readings task")
                .brokerType(BrokerType.kafka)
                .data(carFuelReadings)
                .cronExpression("*/1 * * * * *").build();

        String id1 = this.iotTaskService.createTask(heartRateMeterTask);
        String id2 = this.iotTaskService.createTask(carFuelReadingsTask);
        List<String> tasks = this.iotTaskService.listTasks();
        org.hamcrest.MatcherAssert.assertThat(tasks, hasItems(id1, id2));
    }
}
