package com.internship.books_api.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/profile")
    public Map<String, Object> profile(Authentication authentication) {
        Map<String, Object> info = new HashMap<>();
        if (authentication != null && authentication.getPrincipal() instanceof OAuth2User) {
            OAuth2User user = (OAuth2User) authentication.getPrincipal();
            info.put("name", user.getAttribute("name"));
            info.put("email", user.getAttribute("email"));
            info.put("picture", user.getAttribute("picture"));
            info.put("message", "Logged in via Google");
        } else if (authentication != null) {
            info.put("name", authentication.getName());
            info.put("message", "Logged in");
        } else {
            info.put("message", "Not logged in");
        }
        return info;
    }
}