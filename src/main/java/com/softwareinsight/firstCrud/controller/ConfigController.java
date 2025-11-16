package com.softwareinsight.firstCrud.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/")
public class ConfigController {

    @RequestMapping("health")
    public String health() {
        return "OK";
    }
}
