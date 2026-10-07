package com.internship.student;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final StudentRepository repository;

    public WebSocketController(SimpMessagingTemplate messagingTemplate,
                               StudentRepository repository) {
        this.messagingTemplate = messagingTemplate;
        this.repository = repository;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public Map<String, String> handleChat(Map<String, String> message) {
        Map<String, String> response = new HashMap<>();
        response.put("sender", message.getOrDefault("sender", "Anonymous"));
        response.put("content", message.getOrDefault("content", ""));
        response.put("timestamp", LocalDateTime.now().toString());
        return response;
    }

    @Scheduled(fixedRate = 30000)
    public void broadcastStudentCount() {
        long count = repository.count();
        Map<String, Object> payload = new HashMap<>();
        payload.put("count", count);
        payload.put("timestamp", LocalDateTime.now().toString());
        messagingTemplate.convertAndSend("/topic/students/count", (Object) payload);
    }

    @GetMapping("/api/ws/broadcast")
    public String triggerBroadcast() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("message", "Manual broadcast at " + LocalDateTime.now());
        messagingTemplate.convertAndSend("/topic/notifications", (Object) payload);
        return "Broadcast sent";
    }
}