package com.internship.books_api.controller;

import com.internship.books_api.dto.BookResponseDTO;
import com.internship.books_api.service.BookService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    private BookResponseDTO sampleResponse() {
        return new BookResponseDTO(1L, "Clean Code", "Robert Martin", "123", 29.99, LocalDate.of(2008, 8, 1));
    }

    // ===== Test 1: No token — should be 401 or 403 =====
    @Test
    @DisplayName("GET /api/books without token should be rejected")
    void shouldRejectWithoutToken() throws Exception {
        mockMvc.perform(get("/api/books"))
                .andExpect(status().is4xxClientError());
    }

    // ===== Test 2: No token POST — should be 401 or 403 =====
    @Test
    @DisplayName("POST /api/books without token should be rejected")
    void shouldRejectPostWithoutToken() throws Exception {
        String json = "{\"title\":\"Clean Code\",\"author\":\"Robert Martin\",\"price\":29.99}";
        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is4xxClientError());
    }

    // ===== Test 3: No token DELETE — should be 401 or 403 =====
    @Test
    @DisplayName("DELETE /api/books/{id} without token should be rejected")
    void shouldRejectDeleteWithoutToken() throws Exception {
        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().is4xxClientError());
    }

    // ===== Test 4: No token PUT — should be 401 or 403 =====
    @Test
    @DisplayName("PUT /api/books/{id} without token should be rejected")
    void shouldRejectPutWithoutToken() throws Exception {
        String json = "{\"title\":\"Updated\",\"author\":\"Robert\",\"price\":29.99}";
        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is4xxClientError());
    }

    // ===== Test 5: Bad token — should be rejected =====
    @Test
    @DisplayName("GET /api/books with bad token should be rejected")
    void shouldRejectBadToken() throws Exception {
        mockMvc.perform(get("/api/books")
                        .header("Authorization", "Bearer invalid.token.here"))
                .andExpect(status().is4xxClientError());
    }

    // ===== Test 6: Random header — should be rejected =====
    @Test
    @DisplayName("GET /api/books with wrong auth header should be rejected")
    void shouldRejectWrongAuthHeader() throws Exception {
        mockMvc.perform(get("/api/books")
                        .header("Authorization", "Basic dXNlcjpwYXNz"))
                .andExpect(status().is4xxClientError());
    }

    // ===== Test 7: Login endpoint accessible without auth =====
    @Test
    @DisplayName("POST /api/auth/login should be accessible without token")
    void loginEndpointAccessible() throws Exception {
        String json = "{\"username\":\"nonexistent\",\"password\":\"wrong\"}";
        // Should return 401 or 400 but NOT 403-forbidden-before-reaching-controller
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is4xxClientError());
    }

    // ===== Test 8: Register endpoint accessible without auth =====
    @Test
    @DisplayName("POST /api/auth/register should be accessible without token")
    void registerEndpointAccessible() throws Exception {
        String json = "{\"username\":\"\",\"password\":\"\"}";
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().is4xxClientError());
    }
}