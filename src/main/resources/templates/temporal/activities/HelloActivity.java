package com.sample.demo.temporal.activities;

import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface HelloActivity {
    String sayHello(String name);
}
