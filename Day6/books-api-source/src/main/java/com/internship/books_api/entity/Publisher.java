package com.internship.books_api.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "publishers")
public class Publisher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String country;

    private Integer foundedYear;

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books = new ArrayList<>();

    public Publisher() {
    }

    public Publisher(String name, String country, Integer foundedYear) {
        this.name = name;
        this.country = country;
        this.foundedYear = foundedYear;
    }

    public void addBook(Book book) {
        books.add(book);
        book.setPublisher(this);
    }

    public void removeBook(Book book) {
        books.remove(book);
        book.setPublisher(null);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Integer getFoundedYear() { return foundedYear; }
    public void setFoundedYear(Integer foundedYear) { this.foundedYear = foundedYear; }

    public List<Book> getBooks() { return books; }
    public void setBooks(List<Book> books) { this.books = books; }
}