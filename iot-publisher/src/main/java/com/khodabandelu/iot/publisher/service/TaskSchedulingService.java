package com.khodabandelu.iot.publisher.service;

import java.util.List;

public interface TaskSchedulingService {
    void scheduleTask(String jobId, Runnable tasklet, String cronExpression);

    void removeScheduledTask(String jobId);

    List<String> listTasks();
}
