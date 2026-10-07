package com.internship.books_api.controller;

import com.internship.books_api.dto.BookRequestDTO;
import com.internship.books_api.dto.BookResponseDTO;
import com.internship.books_api.service.BookService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponseDTO> create(@Valid @RequestBody BookRequestDTO dto) {
        return ResponseEntity.ok(bookService.save(dto));
    }

    @GetMapping("/paged")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Page<BookResponseDTO>> getAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        return ResponseEntity.ok(bookService.findAllPaged(page, size, sortBy));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<BookResponseDTO>> getAll() {
        return ResponseEntity.ok(bookService.findAll());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<BookResponseDTO> getById(@PathVariable Long id) {
        return bookService.findById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new com.internship.books_api.exception.ResourceNotFoundException("Book not found: " + id));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookResponseDTO> update(@PathVariable Long id, @Valid @RequestBody BookRequestDTO dto) {
        return ResponseEntity.ok(bookService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<BookResponseDTO>> searchByAuthor(@RequestParam String author) {
        return ResponseEntity.ok(bookService.findByAuthor(author));
    }

    @GetMapping("/above-price")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<BookResponseDTO>> getAbovePrice(@RequestParam Double minPrice) {
        return ResponseEntity.ok(bookService.findBooksAbovePrice(minPrice));
    }

    @GetMapping("/by-publisher")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<BookResponseDTO>> getByPublisher(@RequestParam String name) {
        return ResponseEntity.ok(bookService.findBooksByPublisherName(name));
    }

    @GetMapping("/search-all")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<BookResponseDTO>> searchAll(@RequestParam String keyword) {
        return ResponseEntity.ok(bookService.searchByAuthorOrTitle(keyword));
    }
}