package com.khodabandelu.iot.publisher.service.impl;

import com.khodabandelu.iot.publisher.domain.IotGroupTask;
import com.khodabandelu.iot.publisher.domain.IotTask;
import com.khodabandelu.iot.publisher.service.MessagingService;
import com.khodabandelu.iot.publisher.service.TaskSchedulingService;
import com.khodabandelu.iot.publisher.service.IotTaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IotTaskServiceImpl implements IotTaskService {

    private final TaskSchedulingService taskSchedulingService;
    private final MessagingService messagingService;

    @Override
    public String createTask(IotTask task) {
        task.setId(UUID.randomUUID().toString());
        IotTaskRunnable runnable = new IotTaskRunnable(messagingService);
        runnable.setIotTask(task);
        taskSchedulingService.scheduleTask(task.getId(), runnable, task.getCronExpression());
        return task.getId();
    }

    @Override
    public  List<String> createGroupTask(IotGroupTask groupTask) {
        List<String> ids =  new ArrayList<>();
        groupTask.getData().forEach(data -> {
            IotTaskRunnable runnable = new IotTaskRunnable(messagingService);
            var id=UUID.randomUUID().toString() ;
            ids.add(id);
            var task=IotTask.builder()
                    .id(id)
                    .name(groupTask.getName())
                    .brokerType(groupTask.getBrokerType())
                    .cronExpression(groupTask.getCronExpression())
                    .topic(groupTask.getTopic())
                    .groupId(groupTask.getId())
                    .data(data).build();
            runnable.setIotTask(task);
            taskSchedulingService.scheduleTask(task.getId(), runnable, task.getCronExpression());
        });
        return ids;
    }

    @Override
    public void removeScheduledTask(String taskId) {
        taskSchedulingService.removeScheduledTask(taskId);
    }

    @Override
    public List<String> listTasks() {
        return taskSchedulingService.listTasks();
    }
}
