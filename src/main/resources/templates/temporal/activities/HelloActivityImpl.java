package com.sample.demo.temporal.activities;

import io.temporal.spring.boot.ActivityImpl;
import org.springframework.stereotype.Component;

@Component
@ActivityImpl(taskQueues = "demoqueue")
public class HelloActivityImpl implements HelloActivity {
    @Override
    public String sayHello(String name) {
        return "hello " + name;
    }
}
