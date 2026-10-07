package com.internship.books_api.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetterInjectedService {

    private NotificationService notificationService;

    @Autowired
    public void setNotificationService(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    public String doWork() {
        return "SetterInjectedService -> " + notificationService.notify("Setter injection works");
    }
}