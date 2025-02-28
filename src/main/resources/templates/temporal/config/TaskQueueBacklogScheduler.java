package com.sample.demo.temporal.config;

import com.uber.m3.tally.Scope;
import io.temporal.api.enums.v1.DescribeTaskQueueMode;
import io.temporal.api.enums.v1.TaskQueueKind;
import io.temporal.api.taskqueue.v1.TaskQueue;
import io.temporal.api.workflowservice.v1.DescribeTaskQueueRequest;
import io.temporal.api.workflowservice.v1.DescribeTaskQueueResponse;
import io.temporal.serviceclient.WorkflowServiceStubs;
import io.temporal.spring.boot.autoconfigure.template.WorkersTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class TaskQueueBacklogScheduler {

    @Autowired
    @Qualifier("temporalWorkersTemplate")
    private WorkersTemplate workersTemplate;

    @Autowired
    WorkflowServiceStubs service;

    @Autowired
    @Qualifier("temporalMetricsScope")
    Scope metricsScope;

    @Value("${tmprl.cloud.namespace}")
    private String namespace;

    // report every 10s
    @Scheduled(fixedDelay = 10000)
    public void publishTaskQueueBacklog() {
        List<String> taskQueues = new ArrayList<>();
        workersTemplate.getWorkers().forEach(w -> {
            if(!taskQueues.contains(w.getTaskQueue())) {
                taskQueues.add(w.getTaskQueue());
            }
        });

        for(String tq : taskQueues) {
            DescribeTaskQueueResponse resNonSticky = service.blockingStub().describeTaskQueue(DescribeTaskQueueRequest.newBuilder()
                    .setTaskQueue(TaskQueue.newBuilder()
                            .setName(tq)
                            .setKind(TaskQueueKind.TASK_QUEUE_KIND_NORMAL)
                            .build())
                    .setApiMode(DescribeTaskQueueMode.DESCRIBE_TASK_QUEUE_MODE_ENHANCED)
                    .setReportPollers(true)
                    .setIncludeTaskQueueStatus(true)
                    .setReportStats(true)
                    .setReportTaskReachability(true)
                    .setNamespace(namespace)
                    .build());

            Map<String, String> tags = new HashMap<>();
            tags.put("namespace", namespace);
            tags.put("task-queue", tq);

            metricsScope.tagged(tags).gauge("workflow_task_backlog").update(
                    resNonSticky.getVersionsInfoMap().values().iterator().next().getTypesInfoMap().get(1).getStats().getApproximateBacklogCount()
            );
            metricsScope.tagged(tags).gauge("activity_task_backlog").update(
                    resNonSticky.getVersionsInfoMap().values().iterator().next().getTypesInfoMap().get(1).getStats().getApproximateBacklogCount()
            );
        }
    }
}
