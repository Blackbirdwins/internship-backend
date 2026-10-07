package com.internship.books_api.controller;

import com.internship.books_api.dto.BookResponseDTO;
import com.internship.books_api.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;

@Slf4j
@RestController
@RequestMapping("/api/sse")
public class SseController {

    private final BookService bookService;
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    public SseController(BookService bookService) {
        this.bookService = bookService;
    }

    // ============ BLOCKING SSE (Servlet-based) ============

    @GetMapping(value = "/unicast", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter unicast() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    emitter.send(SseEmitter.event()
                            .name("unicast")
                            .data("Event " + i + " at " + LocalDateTime.now()));
                    Thread.sleep(2000);
                }
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
        return emitter;
    }

    @GetMapping(value = "/subscribe", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(() -> emitters.remove(emitter));
        emitter.onError((e) -> emitters.remove(emitter));
        return emitter;
    }

    @GetMapping("/broadcast")
    public String broadcast() {
        broadcastEvent("Manual broadcast at " + LocalDateTime.now());
        return "Broadcast sent to " + emitters.size() + " clients";
    }

    private void broadcastEvent(String message) {
        List<SseEmitter> dead = new CopyOnWriteArrayList<>();
        emitters.forEach(emitter -> {
            try {
                emitter.send(SseEmitter.event().name("broadcast").data(message));
            } catch (IOException e) {
                dead.add(emitter);
            }
        });
        emitters.removeAll(dead);
    }

    @GetMapping(value = "/books/live", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter liveBooks() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    List<BookResponseDTO> books = bookService.findAll();
                    emitter.send(SseEmitter.event()
                            .name("books")
                            .data("Book count: " + books.size() + " at " + LocalDateTime.now()));
                    Thread.sleep(5000);
                }
                emitter.complete();
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
        return emitter;
    }

    // ============ NON-BLOCKING SSE (Reactive with Flux) ============

    @GetMapping(value = "/reactive/unicast", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> reactiveUnicast() {
        return Flux.interval(Duration.ofSeconds(2))
                .take(10)
                .map(seq -> "Reactive event #" + seq + " at " + LocalDateTime.now());
    }

    @GetMapping(value = "/reactive/multicast", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> reactiveMulticast() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(seq -> "Multicast tick " + seq);
    }

    @GetMapping(value = "/reactive/books", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> reactiveBooks() {
        return Flux.interval(Duration.ofSeconds(3))
                .take(20)
                .map(seq -> "Live book count: " + bookService.findAll().size());
    }
}