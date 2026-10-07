package com.internship.books_api.controller;

import com.internship.books_api.dto.BookResponseDTO;
import com.internship.books_api.exception.ResourceNotFoundException;
import com.internship.books_api.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc(addFilters = false)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @MockitoBean
    private com.internship.books_api.security.JwtFilter jwtFilter;

    @MockitoBean
    private com.internship.books_api.security.JwtUtil jwtUtil;

    @MockitoBean
    private com.internship.books_api.security.CustomUserDetailsService customUserDetailsService;

    private BookResponseDTO sampleResponse() {
        return new BookResponseDTO(1L, "Clean Code", "Robert Martin", "123", 29.99, LocalDate.of(2008, 8, 1));
    }

    @Test
    @DisplayName("GET /api/books should return list of books")
    void shouldGetAllBooks() throws Exception {
        when(bookService.findAll()).thenReturn(Arrays.asList(sampleResponse()));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].title").value("Clean Code"));
    }

    @Test
    @DisplayName("GET /api/books/{id} should return book when exists")
    void shouldGetBookById() throws Exception {
        when(bookService.findById(1L)).thenReturn(Optional.of(sampleResponse()));

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Clean Code"));
    }

    @Test
    @DisplayName("GET /api/books/{id} should return 404 when not found")
    void shouldReturn404WhenBookNotFound() throws Exception {
        when(bookService.findById(999L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("POST /api/books should create book")
    void shouldCreateBook() throws Exception {
        String json = "{\"title\":\"Clean Code\",\"author\":\"Robert Martin\",\"isbn\":\"123\",\"price\":29.99,\"publishedDate\":\"2008-08-01\"}";

        when(bookService.save(any())).thenReturn(sampleResponse());

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Clean Code"));
    }

    @Test
    @DisplayName("POST /api/books should return 400 when title is blank")
    void shouldReturn400WhenTitleBlank() throws Exception {
        String invalid = "{\"title\":\"\",\"author\":\"\",\"price\":-5.0}";

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalid))
                .andExpect(status().isBadRequest());

        verify(bookService, never()).save(any());
    }

    @Test
    @DisplayName("POST /api/books should return 400 when body is invalid JSON")
    void shouldReturn400WhenInvalidJson() throws Exception {
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ invalid json }"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("PUT /api/books/{id} should update book")
    void shouldUpdateBook() throws Exception {
        String json = "{\"title\":\"Clean Code 2nd Ed\",\"author\":\"Robert Martin\",\"price\":34.99}";

        when(bookService.update(eq(1L), any())).thenReturn(sampleResponse());

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /api/books/{id} should return 204")
    void shouldDeleteBook() throws Exception {
        doNothing().when(bookService).deleteById(1L);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /api/books/{id} should return 404 when not found")
    void shouldReturn404WhenDeletingNonExistent() throws Exception {
        doThrow(new ResourceNotFoundException("Book not found: 999"))
                .when(bookService).deleteById(999L);

        mockMvc.perform(delete("/api/books/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GET /api/books/search should return matching books")
    void shouldSearchByAuthor() throws Exception {
        when(bookService.findByAuthor("Robert Martin")).thenReturn(Arrays.asList(sampleResponse()));

        mockMvc.perform(get("/api/books/search").param("author", "Robert Martin"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}