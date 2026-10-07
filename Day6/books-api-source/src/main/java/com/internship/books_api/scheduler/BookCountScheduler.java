package com.internship.books_api.scheduler;

import com.internship.books_api.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BookCountScheduler {

    private final BookRepository bookRepository;

    public BookCountScheduler(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Har 60 seconds (1 minute) baad chalo
    @Scheduled(fixedRate = 60000)
    public void logBookCount() {
        long count = bookRepository.count();
        log.info("Scheduled Task: Total books in database = {}", count);
    }
}