package com.sample.demo.temporal.activities;

import com.sample.demo.temporal.config.MessageController;
import io.temporal.activity.Activity;
import io.temporal.failure.ApplicationFailure;
import io.temporal.spring.boot.ActivityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Random;

@Component
@ActivityImpl(taskQueues = "demoqueue-two")
public class MessageActivityImpl implements MessageActivity {

    @Autowired
    private MessageController messageController;

    @Override
    public void sendMessage(String message) {
        // throw random failure for demo 20% of time
        throwRandomFailure(Activity.getExecutionContext().getInfo().getActivityType());

        SseEmitter latestEm = messageController.getLatestEmitter();
        try {
            latestEm.send(message);
        } catch (IOException e) {
            latestEm.completeWithError(e);
        }
    }

    private void throwRandomFailure(String activityName) {
        Random random = new Random();
        double randomValue = random.nextDouble();
        if (randomValue < 0.20) { // 10% chance of failure
            throw ApplicationFailure.newFailure("Error sending message for activity " + activityName,
                    "Retrying", null);
        }
    }
}
