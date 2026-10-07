package com.internship.books_api.service;

import com.internship.books_api.dto.AuthRequestDTO;
import com.internship.books_api.dto.AuthResponseDTO;

public interface AuthService {

    AuthResponseDTO register(AuthRequestDTO request);

    AuthResponseDTO login(AuthRequestDTO request);
}