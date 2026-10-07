package com.internship.books_api.controller;

import com.internship.books_api.dto.BookResponseDTO;
import com.internship.books_api.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class WebSocketController {

    private final SimpMessagingTemplate messagingTemplate;
    private final BookService bookService;

    public WebSocketController(SimpMessagingTemplate messagingTemplate, BookService bookService) {
        this.messagingTemplate = messagingTemplate;
        this.bookService = bookService;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public Map<String, String> handleChat(Map<String, String> message) {
        log.info(">>> WebSocket chat: {}", message.get("content"));
        Map<String, String> response = new HashMap<>();
        response.put("sender", message.getOrDefault("sender", "Anonymous"));
        response.put("content", message.getOrDefault("content", ""));
        response.put("timestamp", LocalDateTime.now().toString());
        return response;
    }

    @Scheduled(fixedRate = 5000)
    public void broadcastBookCount() {
        List<BookResponseDTO> books = bookService.findAll();
        Map<String, Object> payload = new HashMap<>();
        payload.put("count", books.size());
        payload.put("timestamp", LocalDateTime.now().toString());
        messagingTemplate.convertAndSend("/topic/books/count", (Object) payload);
    }

    @GetMapping("/api/ws/broadcast")
    @ResponseBody
    public String triggerBroadcast() {
        Map<String, Object> payload = new HashMap<>();
        payload.put("message", "Manual broadcast at " + LocalDateTime.now());
        payload.put("type", "info");
        messagingTemplate.convertAndSend("/topic/notifications", (Object) payload);
        return "Broadcast sent";
    }

    @GetMapping("/api/ws/live-books")
    @ResponseBody
    public String liveBooks() {
        List<BookResponseDTO> books = bookService.findAll();
        Map<String, Object> payload = new HashMap<>();
        payload.put("books", books);
        payload.put("timestamp", LocalDateTime.now().toString());
        messagingTemplate.convertAndSend("/topic/books/live", (Object) payload);
        return "Books sent";
    }
}