package com.sample.demo.temporal.config;

import io.temporal.worker.WorkerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomShutdownListener implements ApplicationListener<ContextClosedEvent> {

    @Autowired
    @Qualifier("temporalWorkerFactory")
    WorkerFactory workerFactory;

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("************** Graceful WorkerFactory shutdown started...");
        try {
            workerFactory.shutdown();
            workerFactory.awaitTermination(20, TimeUnit.SECONDS);
        } catch (Throwable e) {
            System.out.println("******** EE: " + e.getClass().getName());
        }
        System.out.println("************** Graceful WorkerFactory shutdown completed...");
    }
}
