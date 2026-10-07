package com.internship.books_api.service.impl;

import com.internship.books_api.dto.BookMapper;
import com.internship.books_api.dto.BookRequestDTO;
import com.internship.books_api.dto.BookResponseDTO;
import com.internship.books_api.entity.Book;
import com.internship.books_api.entity.Publisher;
import com.internship.books_api.exception.ResourceNotFoundException;
import com.internship.books_api.repository.BookRepository;
import com.internship.books_api.repository.PublisherRepository;
import com.internship.books_api.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public BookServiceImpl(BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
        log.info("BookServiceImpl initialized");
    }

    @Override
    public BookResponseDTO save(BookRequestDTO dto) {
        log.debug("Saving book: title='{}'", dto.getTitle());
        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new IllegalArgumentException("Book title is required");
        }
        Book book = BookMapper.toEntity(dto);
        if (dto.getPublisherId() != null) {
            Publisher publisher = publisherRepository.findById(dto.getPublisherId())
                    .orElseThrow(() -> new ResourceNotFoundException("Publisher not found: " + dto.getPublisherId()));
            book.setPublisher(publisher);
        }
        Book saved = bookRepository.save(book);
        log.info("Book saved: id={}", saved.getId());
        return BookMapper.toResponseDTO(saved);
    }

    @Override
    public List<BookResponseDTO> findAll() {
        return bookRepository.findAll().stream()
                .map(BookMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<BookResponseDTO> findAllPaged(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return bookRepository.findAll(pageable).map(BookMapper::toResponseDTO);
    }

    @Override
    public Optional<BookResponseDTO> findById(Long id) {
        return bookRepository.findById(id).map(BookMapper::toResponseDTO);
    }

    @Override
    public BookResponseDTO update(Long id, BookRequestDTO dto) {
        Book existing = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found: " + id));
        BookMapper.updateEntityFromDTO(existing, dto);
        if (dto.getPublisherId() != null) {
            Publisher publisher = publisherRepository.findById(dto.getPublisherId())
                    .orElseThrow(() -> new ResourceNotFoundException("Publisher not found"));
            existing.setPublisher(publisher);
        }
        Book updated = bookRepository.save(existing);
        return BookMapper.toResponseDTO(updated);
    }

    @Override
    public void deleteById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found: " + id);
        }
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookResponseDTO> findByAuthor(String author) {
        return bookRepository.findByAuthor(author).stream()
                .map(BookMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponseDTO> findBooksAbovePrice(Double minPrice) {
        return bookRepository.findBooksAbovePrice(minPrice).stream()
                .map(BookMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponseDTO> findBooksByPublisherName(String publisherName) {
        return bookRepository.findBooksByPublisherName(publisherName).stream()
                .map(BookMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookResponseDTO> searchByAuthorOrTitle(String keyword) {
        return bookRepository.searchByAuthorOrTitle(keyword).stream()
                .map(BookMapper::toResponseDTO)
                .collect(Collectors.toList());
    }
}