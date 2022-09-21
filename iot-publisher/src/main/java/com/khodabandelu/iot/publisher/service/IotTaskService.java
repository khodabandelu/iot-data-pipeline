package com.khodabandelu.iot.publisher.service;

import com.khodabandelu.iot.publisher.domain.IotGroupTask;
import com.khodabandelu.iot.publisher.domain.IotTask;

import java.util.List;

public interface IotTaskService {
    String createTask(IotTask task);

    List<String> createGroupTask(IotGroupTask groupTask);

    void removeScheduledTask(String taskId);

    List<String> listTasks();
}
