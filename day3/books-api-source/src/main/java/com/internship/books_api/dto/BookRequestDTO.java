package com.internship.books_api.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class BookRequestDTO {

    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 200, message = "Title must be between 1 and 200 characters")
    private String title;

    @NotBlank(message = "Author is required")
    @Size(min = 2, max = 100, message = "Author name must be between 2 and 100 characters")
    private String author;

    @Size(max = 20, message = "ISBN must be at most 20 characters")
    private String isbn;

    @NotNull(message = "Price is required")
    @Min(value = 0, message = "Price cannot be negative")
    @Max(value = 10000, message = "Price cannot exceed 10000")
    private Double price;

    @PastOrPresent(message = "Published date cannot be in the future")
    private LocalDate publishedDate;

    private Long publisherId;

    public BookRequestDTO() {
    }

    public BookRequestDTO(String title, String author, String isbn, Double price, LocalDate publishedDate) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
        this.publishedDate = publishedDate;
    }

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

    public Long getPublisherId() { return publisherId; }
    public void setPublisherId(Long publisherId) { this.publisherId = publisherId; }
}