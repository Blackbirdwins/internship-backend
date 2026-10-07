package com.internship.books_api.service;

import com.internship.books_api.dto.BookRequestDTO;
import com.internship.books_api.dto.BookResponseDTO;

import java.util.List;
import java.util.Optional;

public interface BookService {

    BookResponseDTO save(BookRequestDTO dto);

    List<BookResponseDTO> findAll();

    Optional<BookResponseDTO> findById(Long id);

    BookResponseDTO update(Long id, BookRequestDTO dto);

    void deleteById(Long id);

    List<BookResponseDTO> findByAuthor(String author);
}