package com.internship.books_api.controller;

import com.internship.books_api.dto.BookEventDTO;
import com.internship.books_api.kafka.KafkaProducerService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/kafka")
public class KafkaController {

    private final KafkaProducerService producerService;

    public KafkaController(KafkaProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/send")
    public Map<String, String> send(@RequestBody Map<String, String> body) {
        String message = body.getOrDefault("message", "Test message");
        producerService.sendMessage(message);
        return Map.of("status", "sent", "message", message);
    }

    @GetMapping("/send-test")
    public Map<String, String> sendTest() {
        producerService.sendMessage("Test message at " + System.currentTimeMillis());
        return Map.of("status", "sent");
    }

    @PostMapping("/send-book-json")
    public Map<String, Object> sendBookJson(@RequestBody BookEventDTO event) {
        producerService.sendBookEventAsJson(event);
        return Map.of("status", "sent", "event", event);
    }
}