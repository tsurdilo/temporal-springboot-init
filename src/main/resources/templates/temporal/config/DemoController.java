package com.sample.demo.temporal.config;

import com.sample.demo.temporal.model.Person;
import com.sample.demo.temporal.workflows.HelloWorkflow;
import com.sample.demo.temporal.workflows.MessageWorkflow;
import io.temporal.client.WorkflowClient;
import io.temporal.client.WorkflowOptions;
import io.temporal.client.WorkflowStub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DemoController {
    @Autowired
    WorkflowClient workflowClient;

    @PostMapping(
            value = "/runsample",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.TEXT_HTML_VALUE})
    ResponseEntity helloSample(@RequestBody Person person) {
        HelloWorkflow workflow =
                workflowClient.newWorkflowStub(
                        HelloWorkflow.class,
                        WorkflowOptions.newBuilder()
                                .setTaskQueue("demoqueue")
                                .setWorkflowId("hello-workflow")
                                .build());

        // bypass thymeleaf, don't return template name just result
        return new ResponseEntity<>("\"" + workflow.sayHello(person) + "\"", HttpStatus.OK);
    }

    @PostMapping(
            value = "/runsampletwo",
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.TEXT_HTML_VALUE})
    ResponseEntity helloSampleTwo(@RequestBody String message) {

        MessageWorkflow workflow = workflowClient.newWorkflowStub(MessageWorkflow.class,
                WorkflowOptions.newBuilder()
                        .setTaskQueue("demoqueue-two")
                        .setWorkflowId("message-workflow")
                        .build());

        WorkflowClient.start(workflow::sendMessages, message);

        WorkflowStub.fromTyped(workflow).getResult(Void.class);

        return new ResponseEntity<>("\" Message workflow completed\"", HttpStatus.OK);
    }

    @GetMapping("/rundemo")
    public String runDemo(Model model) {
        return "run";
    }

    @GetMapping("/rundemotwo")
    public String runDemoTwo(Model model) {
        return "runtwo";
    }
}