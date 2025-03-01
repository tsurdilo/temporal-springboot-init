package com.sample.demo.temporal.workflows;

import com.sample.demo.temporal.activities.MessageActivity;
import io.temporal.activity.ActivityOptions;
import io.temporal.spring.boot.WorkflowImpl;
import io.temporal.workflow.Async;
import io.temporal.workflow.Promise;
import io.temporal.workflow.Workflow;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@WorkflowImpl(taskQueues = "demoqueue-two")
public class MessageWorkflowImpl implements MessageWorkflow {
    MessageActivity activity = Workflow.newActivityStub(MessageActivity.class,
            ActivityOptions.newBuilder()
                    .setStartToCloseTimeout(Duration.ofSeconds(3))
                    .build());

    @Override
    public void sendMessages(String message) {

        for(int i=0;i<3;i++) {
            sendMessageBatch(i, message);
        }
    }

    private void sendMessageBatch(int i, String message) {
        List<Promise<Void>> messagePromises = new ArrayList<>();
        for(int j = 0; j< 50; j++) {
            messagePromises.add(Async.procedure(activity::sendMessage, message + "-" + i + "-" + j));
        }
        Promise.allOf(messagePromises).get();
        for(Promise<Void> p : messagePromises) {
            if(p.getFailure() != null) {
                System.out.println("Error sending message: " + p.getFailure().getMessage());
            } else {
                p.get();
            }
        }
    }
}
