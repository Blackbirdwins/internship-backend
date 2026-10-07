package com.internship.books_api.dto;

public class BookEventDTO {

    private String eventType;
    private Long bookId;
    private String title;
    private String author;

    public BookEventDTO() {
    }

    public BookEventDTO(String eventType, Long bookId, String title, String author) {
        this.eventType = eventType;
        this.bookId = bookId;
        this.title = title;
        this.author = author;
    }

    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }

    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    @Override
    public String toString() {
        return "BookEventDTO{eventType='" + eventType + "', bookId=" + bookId +
                ", title='" + title + "', author='" + author + "'}";
    }
}