package com.internship.books_api.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/rest-demo")
public class RestClientDemoController {

    private final RestTemplate restTemplate;
    private final WebClient webClient;

    public RestClientDemoController() {
        this.restTemplate = new RestTemplate();
        this.webClient = WebClient.builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .build();
    }

    // ============ RestTemplate (BLOCKING) ============

    @GetMapping("/resttemplate/get")
    public Map restTemplateGet() {
        log.info(">>> RestTemplate GET call");
        return restTemplate.getForObject(
                "https://jsonplaceholder.typicode.com/posts/1", Map.class);
    }

    @GetMapping("/resttemplate/post")
    public Map restTemplatePost() {
        log.info(">>> RestTemplate POST call");
        Map<String, Object> body = Map.of("title", "Test", "body", "Body", "userId", 1);
        return restTemplate.postForObject(
                "https://jsonplaceholder.typicode.com/posts", body, Map.class);
    }

    @GetMapping("/resttemplate/put")
    public Map restTemplatePut() {
        log.info(">>> RestTemplate PUT call");
        Map<String, Object> body = Map.of("id", 1, "title", "Updated", "body", "Body", "userId", 1);
        restTemplate.put("https://jsonplaceholder.typicode.com/posts/1", body);
        return Map.of("status", "PUT completed");
    }

    @GetMapping("/resttemplate/delete")
    public Map restTemplateDelete() {
        log.info(">>> RestTemplate DELETE call");
        restTemplate.delete("https://jsonplaceholder.typicode.com/posts/1");
        return Map.of("status", "DELETE completed");
    }

    // ============ WebClient (REACTIVE / NON-BLOCKING) ============

    @GetMapping("/webclient/get")
    public Mono<Map> webClientGet() {
        log.info(">>> WebClient GET call");
        return webClient.get().uri("/posts/1").retrieve().bodyToMono(Map.class);
    }

    @GetMapping("/webclient/post")
    public Mono<Map> webClientPost() {
        log.info(">>> WebClient POST call");
        Map<String, Object> body = Map.of("title", "Test", "body", "Body", "userId", 1);
        return webClient.post().uri("/posts").bodyValue(body).retrieve().bodyToMono(Map.class);
    }

    @GetMapping("/webclient/put")
    public Mono<Map> webClientPut() {
        log.info(">>> WebClient PUT call");
        Map<String, Object> body = Map.of("id", 1, "title", "Updated", "body", "Body", "userId", 1);
        return webClient.put().uri("/posts/1").bodyValue(body).retrieve().bodyToMono(Map.class);
    }

    @GetMapping("/webclient/delete")
    public Mono<String> webClientDelete() {
        log.info(">>> WebClient DELETE call");
        return webClient.delete().uri("/posts/1").retrieve()
                .bodyToMono(String.class).defaultIfEmpty("DELETE completed");
    }
}