package com.internship.books_api.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    private String isbn;

    private Double price;

    private LocalDate publishedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    public Book() {
    }

    public Book(String title, String author, String isbn, Double price, LocalDate publishedDate) {
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

    public Publisher getPublisher() { return publisher; }
    public void setPublisher(Publisher publisher) { this.publisher = publisher; }
}