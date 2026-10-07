package com.internship.books_api.controller;

import com.internship.books_api.dto.BookRequestDTO;
import com.internship.books_api.dto.BookResponseDTO;
import com.internship.books_api.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
        log.info("BookController initialized with BookService");
    }

    @PostMapping
    public ResponseEntity<BookResponseDTO> create(@RequestBody BookRequestDTO dto) {
        log.info("POST /api/books — creating new book");
        return ResponseEntity.ok(bookService.save(dto));
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAll() {
        log.info("GET /api/books — fetching all books");
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getById(@PathVariable Long id) {
        log.info("GET /api/books/{} — fetching book by id", id);
        return bookService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> update(@PathVariable Long id, @RequestBody BookRequestDTO dto) {
        log.info("PUT /api/books/{} — updating book", id);
        try {
            return ResponseEntity.ok(bookService.update(id, dto));
        } catch (RuntimeException e) {
            log.error("Update failed for id={}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.info("DELETE /api/books/{} — deleting book", id);
        try {
            bookService.deleteById(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            log.error("Delete failed for id={}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookResponseDTO>> searchByAuthor(@RequestParam String author) {
        log.info("GET /api/books/search?author={} — searching", author);
        return ResponseEntity.ok(bookService.findByAuthor(author));
    }
}