package com.internship.books_api.di;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class NotificationService {

    public String notify(String message) {
        log.info("Notification sent: {}", message);
        return "Notified: " + message;
    }
}