package com.lrtf.messaging.controller;


import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaStatusController {

    @GetMapping("/status")
    public ResponseEntity<?> status() {
        Map<String, String> msg = new HashMap<>();
        msg.put("status", "Kafka is up and running!");
        return ResponseEntity.ok(msg);
    }

}
