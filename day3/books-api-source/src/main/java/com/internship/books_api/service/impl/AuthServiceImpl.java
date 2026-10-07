package com.internship.books_api.service.impl;

import com.internship.books_api.dto.AuthRequestDTO;
import com.internship.books_api.dto.AuthResponseDTO;
import com.internship.books_api.entity.User;
import com.internship.books_api.exception.ResourceNotFoundException;
import com.internship.books_api.repository.UserRepository;
import com.internship.books_api.security.JwtUtil;
import com.internship.books_api.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           JwtUtil jwtUtil,
                           AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public AuthResponseDTO register(AuthRequestDTO request) {
        log.debug("Registering user: {}", request.getUsername());

        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username already taken: " + request.getUsername());
        }

        User user = new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                "USER"
        );
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
        log.info("User registered: {}", user.getUsername());

        return new AuthResponseDTO(token, user.getUsername(), user.getRole(),
                "Registration successful");
    }

    @Override
    public AuthResponseDTO login(AuthRequestDTO request) {
        log.debug("Login attempt: {}", request.getUsername());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            log.warn("Login failed for: {}", request.getUsername());
            throw new BadCredentialsException("Invalid username or password");
        }

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
        log.info("Login successful: {}", user.getUsername());

        return new AuthResponseDTO(token, user.getUsername(), user.getRole(),
                "Login successful");
    }
}