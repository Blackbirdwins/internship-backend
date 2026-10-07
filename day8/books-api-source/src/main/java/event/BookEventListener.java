package com.internship.books_api.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BookEventListener {

    @EventListener
    public void handleBookCreated(BookCreatedEvent event) {
        log.info(">>> EVENT RECEIVED: Book created - id={}, title='{}'",
                event.getBook().getId(), event.getBook().getTitle());
    }

    @Async
    @EventListener
    public void handleBookCreatedAsync(BookCreatedEvent event) {
        log.info(">>> ASYNC EVENT: Processing in background - id={}",
                event.getBook().getId());
    }
}