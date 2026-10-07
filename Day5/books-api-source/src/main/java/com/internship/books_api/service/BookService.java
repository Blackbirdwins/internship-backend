package com.internship.books_api.service;

import com.internship.books_api.dto.BookRequestDTO;
import com.internship.books_api.dto.BookResponseDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface BookService {

    BookResponseDTO save(BookRequestDTO dto);

    List<BookResponseDTO> findAll();

    Page<BookResponseDTO> findAllPaged(int page, int size, String sortBy);

    Optional<BookResponseDTO> findById(Long id);

    BookResponseDTO update(Long id, BookRequestDTO dto);

    void deleteById(Long id);

    List<BookResponseDTO> findByAuthor(String author);

    List<BookResponseDTO> findBooksAbovePrice(Double minPrice);

    List<BookResponseDTO> findBooksByPublisherName(String publisherName);

    List<BookResponseDTO> searchByAuthorOrTitle(String keyword);
}