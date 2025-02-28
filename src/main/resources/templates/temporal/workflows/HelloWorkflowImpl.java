package com.sample.demo.temporal.workflows;

import com.sample.demo.temporal.activities.HelloActivity;
import io.temporal.activity.ActivityOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;

import java.time.Duration;

@WorkflowImpl(taskQueues = "demoqueue")
public class HelloWorkflowImpl implements HelloWorkflow {
    @Override
    public String sayHello(String greeting) {
        HelloActivity activity = Workflow.newActivityStub(HelloActivity.class, ActivityOptions.newBuilder()
                .setStartToCloseTimeout(Duration.ofSeconds(2))
                .build());
        Workflow.sleep(Duration.ofSeconds(5));

        return activity.sayHello(greeting);
    }
}
