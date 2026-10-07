package com.internship.books_api.controller;

import com.internship.books_api.dto.AuthRequestDTO;
import com.internship.books_api.dto.AuthResponseDTO;
import com.internship.books_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody AuthRequestDTO request) {
        log.info("POST /api/auth/register — {}", request.getUsername());
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO request) {
        log.info("POST /api/auth/login — {}", request.getUsername());
        return ResponseEntity.ok(authService.login(request));
    }
}