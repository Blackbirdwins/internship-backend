package com.internship.books_api.util;

import org.springframework.stereotype.Component;

@Component
public class BookMapperHelper {

    public String formatTitle(String title) {
        if (title == null || title.isBlank()) {
            return "Untitled";
        }
        return title.trim().toUpperCase();
    }
}