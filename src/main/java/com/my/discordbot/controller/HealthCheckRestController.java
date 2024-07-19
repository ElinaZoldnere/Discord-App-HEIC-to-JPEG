package com.my.discordbot.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
class HealthCheckRestController {

    @GetMapping("/")
    public ResponseEntity<String> checkHealth() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }

}
