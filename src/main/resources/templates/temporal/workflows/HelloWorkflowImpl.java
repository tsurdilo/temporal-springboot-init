package com.sample.demo.temporal.workflows;

import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Workflow;

import java.time.Duration;

@WorkflowImpl(taskQueues = "demoqueue")
public class HelloWorkflowImpl implements HelloWorkflow {
    @Override
    public String sayHello(String greeting) {
        Workflow.sleep(Duration.ofSeconds(5));
        return "hello " + greeting;
    }
}
