package com.sample.demo.temporal.activities;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface MessageActivity {
    void sendMessage(String message);
}
