package com.example.sensormonitoringserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class SensorController {

    @ResponseBody
    @GetMapping("/api/hello")
    public String test() {
        return "Hello, world!";
    }
}
