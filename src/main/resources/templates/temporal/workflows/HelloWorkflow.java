package com.sample.demo.temporal.workflows;

import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
public interface HelloWorkflow {
    @WorkflowMethod
    String sayHello(String greeting);
}
