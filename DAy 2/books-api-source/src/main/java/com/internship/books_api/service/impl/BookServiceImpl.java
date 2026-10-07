package com.internship.books_api.service.impl;

import com.internship.books_api.dto.BookMapper;
import com.internship.books_api.dto.BookRequestDTO;
import com.internship.books_api.dto.BookResponseDTO;
import com.internship.books_api.entity.Book;
import com.internship.books_api.repository.BookRepository;
import com.internship.books_api.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        log.info("BookServiceImpl initialized with BookRepository");
    }

    @Override
    public BookResponseDTO save(BookRequestDTO dto) {
        log.debug("Attempting to save book: title='{}', author='{}'", dto.getTitle(), dto.getAuthor());

        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            log.warn("Save rejected: book title is missing or blank");
            throw new RuntimeException("Book title is required");
        }

        Book book = BookMapper.toEntity(dto);
        Book saved = bookRepository.save(book);

        log.info("Book saved successfully with id={}, title='{}'", saved.getId(), saved.getTitle());
        return BookMapper.toResponseDTO(saved);
    }

    @Override
    public List<BookResponseDTO> findAll() {
        log.debug("Fetching all books");
        List<Book> books = bookRepository.findAll();
        log.info("Retrieved {} books from database", books.size());

        return books.stream()
                .map(BookMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BookResponseDTO> findById(Long id) {
        log.debug("Fetching book by id={}", id);

        Optional<BookResponseDTO> result = bookRepository.findById(id).map(BookMapper::toResponseDTO);
        if (result.isPresent()) {
            log.info("Book found: id={}", id);
        } else {
            log.warn("Book not found: id={}", id);
        }
        return result;
    }

    @Override
    public BookResponseDTO update(Long id, BookRequestDTO dto) {
        log.debug("Updating book id={} with new data: title='{}'", id, dto.getTitle());

        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Update failed: book id={} not found", id);
                    return new RuntimeException("Book not found with id: " + id);
                });

        BookMapper.updateEntityFromDTO(existing, dto);
        Book updated = bookRepository.save(existing);

        log.info("Book updated: id={}, new title='{}'", updated.getId(), updated.getTitle());
        return BookMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteById(Long id) {
        log.debug("Deleting book id={}", id);

        if (!bookRepository.existsById(id)) {
            log.error("Delete failed: book id={} not found", id);
            throw new RuntimeException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);

        log.info("Book deleted: id={}", id);
    }

    @Override
    public List<BookResponseDTO> findByAuthor(String author) {
        log.debug("Searching books by author='{}'", author);

        List<Book> books = bookRepository.findByAuthor(author);
        log.info("Search by author='{}' returned {} books", author, books.size());

        return books.stream()
                .map(BookMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}