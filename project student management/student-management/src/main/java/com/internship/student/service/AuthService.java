package com.internship.student.service;

import com.internship.student.dto.AuthRequestDTO;
import com.internship.student.dto.AuthResponseDTO;
import com.internship.student.security.JwtUtil;

public interface AuthService {
    AuthResponseDTO register(AuthRequestDTO request);
    AuthResponseDTO login(AuthRequestDTO request);
}