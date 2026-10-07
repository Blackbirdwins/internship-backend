package com.internship.books_api.dto;

import com.internship.books_api.entity.Book;

public class BookMapper {

    // Entity -> Response DTO
    public static BookResponseDTO toResponseDTO(Book book) {
        if (book == null) return null;
        return new BookResponseDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getPrice(),
                book.getPublishedDate()
        );
    }

    // Request DTO -> Entity
    public static Book toEntity(BookRequestDTO dto) {
        if (dto == null) return null;
        Book book = new Book();
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setPrice(dto.getPrice());
        book.setPublishedDate(dto.getPublishedDate());
        return book;
    }

    // Apply Request DTO to existing Entity (for updates)
    public static void updateEntityFromDTO(Book existing, BookRequestDTO dto) {
        existing.setTitle(dto.getTitle());
        existing.setAuthor(dto.getAuthor());
        existing.setIsbn(dto.getIsbn());
        existing.setPrice(dto.getPrice());
        existing.setPublishedDate(dto.getPublishedDate());
    }
}