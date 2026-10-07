package com.internship.books_api.di;

import org.springframework.stereotype.Service;

@Service
public class ConstructorInjectedService {

    private final NotificationService notificationService;

    public ConstructorInjectedService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public String doWork() {
        return "ConstructorInjectedService -> " + notificationService.notify("Constructor injection works");
    }
}