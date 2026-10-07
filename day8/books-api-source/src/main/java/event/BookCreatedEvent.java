package com.internship.books_api.event;

import com.internship.books_api.dto.BookResponseDTO;
import org.springframework.context.ApplicationEvent;

public class BookCreatedEvent extends ApplicationEvent {

    private final BookResponseDTO book;

    public BookCreatedEvent(Object source, BookResponseDTO book) {
        super(source);
        this.book = book;
    }

    public BookResponseDTO getBook() {
        return book;
    }
}