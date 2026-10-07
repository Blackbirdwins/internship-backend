package com.internship.books_api.dto;

import java.time.LocalDate;

public class BookResponseDTO {

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private Double price;
    private LocalDate publishedDate;

    public BookResponseDTO() {
    }

    public BookResponseDTO(Long id, String title, String author, String isbn, Double price, LocalDate publishedDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
        this.publishedDate = publishedDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public LocalDate getPublishedDate() { return publishedDate; }
    public void setPublishedDate(LocalDate publishedDate) { this.publishedDate = publishedDate; }
}