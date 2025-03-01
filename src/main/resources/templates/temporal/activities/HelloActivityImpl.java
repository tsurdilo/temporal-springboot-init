package com.sample.demo.temporal.activities;

import com.sample.demo.temporal.model.Person;
import io.temporal.spring.boot.ActivityImpl;
import org.springframework.stereotype.Component;

@Component
@ActivityImpl(taskQueues = "demoqueue")
public class HelloActivityImpl implements HelloActivity {
    @Override
    public String sayHello(Person person) {
        return "Hello " + person.getFirstName() + " " + person.getLastName();
    }
}
