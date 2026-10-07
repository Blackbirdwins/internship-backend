package com.internship.books_api.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FieldInjectedService {

    @Autowired
    private NotificationService notificationService;

    public String doWork() {
        return "FieldInjectedService -> " + notificationService.notify("Field injection works");
    }
}