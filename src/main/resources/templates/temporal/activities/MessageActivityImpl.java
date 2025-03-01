package com.sample.demo.temporal.activities;

import com.sample.demo.temporal.config.MessageController;
import io.temporal.spring.boot.ActivityImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;

@Component
@ActivityImpl(taskQueues = "demoqueue-two")
public class MessageActivityImpl implements MessageActivity {

    @Autowired
    private MessageController messageController;

    @Override
    public void sendMessage(String message) {
        SseEmitter latestEm = messageController.getLatestEmitter();
        try {
            latestEm.send(message);
        } catch (IOException e) {
            latestEm.completeWithError(e);
        }
    }
}
