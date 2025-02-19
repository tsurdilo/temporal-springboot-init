package com.sample.demo.temporal.config;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/hello")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}