package com.internship.books_api.service;

import com.internship.books_api.dto.BookRequestDTO;
import com.internship.books_api.dto.BookResponseDTO;
import com.internship.books_api.entity.Book;
import com.internship.books_api.entity.Publisher;
import com.internship.books_api.exception.ResourceNotFoundException;
import com.internship.books_api.repository.BookRepository;
import com.internship.books_api.repository.PublisherRepository;
import com.internship.books_api.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PublisherRepository publisherRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book testBook;
    private Publisher testPublisher;
    private BookRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        testPublisher = new Publisher("OReilly", "USA", 1978);
        testPublisher.setId(1L);

        testBook = new Book("Clean Code", "Robert Martin", "123", 29.99, LocalDate.of(2008, 8, 1));
        testBook.setId(1L);
        testBook.setPublisher(testPublisher);

        requestDTO = new BookRequestDTO();
        requestDTO.setTitle("Clean Code");
        requestDTO.setAuthor("Robert Martin");
        requestDTO.setIsbn("123");
        requestDTO.setPrice(29.99);
        requestDTO.setPublishedDate(LocalDate.of(2008, 8, 1));
        requestDTO.setPublisherId(1L);
    }

    @Test
    @DisplayName("Should save book successfully")
    void shouldSaveBook() {
        when(publisherRepository.findById(1L)).thenReturn(Optional.of(testPublisher));
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        BookResponseDTO result = bookService.save(requestDTO);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Clean Code");
        assertThat(result.getId()).isEqualTo(1L);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("Should throw exception when title is blank")
    void shouldThrowWhenTitleIsBlank() {
        requestDTO.setTitle("");
        assertThatThrownBy(() -> bookService.save(requestDTO))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("title");
        verify(bookRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should find book by ID when exists")
    void shouldFindBookById() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
        Optional<BookResponseDTO> result = bookService.findById(1L);
        assertThat(result).isPresent();
        assertThat(result.get().getTitle()).isEqualTo("Clean Code");
    }

    @Test
    @DisplayName("Should return empty when book not found")
    void shouldReturnEmptyWhenBookNotFound() {
        when(bookRepository.findById(999L)).thenReturn(Optional.empty());
        Optional<BookResponseDTO> result = bookService.findById(999L);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Should return all books")
    void shouldReturnAllBooks() {
        Book book2 = new Book("Effective Java", "Joshua Bloch", "456", 45.99, LocalDate.of(2018, 1, 6));
        book2.setId(2L);
        when(bookRepository.findAll()).thenReturn(Arrays.asList(testBook, book2));

        List<BookResponseDTO> result = bookService.findAll();

        assertThat(result).hasSize(2);
    }

    @Test
    @DisplayName("Should update book successfully")
    void shouldUpdateBook() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
        when(publisherRepository.findById(1L)).thenReturn(Optional.of(testPublisher));
        when(bookRepository.save(any(Book.class))).thenReturn(testBook);

        BookResponseDTO result = bookService.update(1L, requestDTO);

        assertThat(result).isNotNull();
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    @DisplayName("Should throw when updating non-existent book")
    void shouldThrowWhenUpdatingNonExistentBook() {
        when(bookRepository.findById(999L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> bookService.update(999L, requestDTO))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("Should delete book successfully")
    void shouldDeleteBook() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        bookService.deleteById(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw when deleting non-existent book")
    void shouldThrowWhenDeletingNonExistentBook() {
        when(bookRepository.existsById(999L)).thenReturn(false);
        assertThatThrownBy(() -> bookService.deleteById(999L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("Should find books by author")
    void shouldFindBooksByAuthor() {
        when(bookRepository.findByAuthor("Robert Martin")).thenReturn(Arrays.asList(testBook));
        List<BookResponseDTO> result = bookService.findByAuthor("Robert Martin");
        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("Should find books above price")
    void shouldFindBooksAbovePrice() {
        when(bookRepository.findBooksAbovePrice(20.0)).thenReturn(Arrays.asList(testBook));
        List<BookResponseDTO> result = bookService.findBooksAbovePrice(20.0);
        assertThat(result).hasSize(1);
    }
}