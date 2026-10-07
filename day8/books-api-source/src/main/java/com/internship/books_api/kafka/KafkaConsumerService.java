package com.internship.books_api.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.internship.books_api.dto.BookEventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaConsumerService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "books-events", groupId = "books-api-group")
    public void consume(String message) {
        log.info(">>> Kafka raw message received: {}", message);

        try {
            BookEventDTO event = objectMapper.readValue(message, BookEventDTO.class);
            log.info(">>> Kafka JSON parsed -> eventType={}, bookId={}, title={}, author={}",
                    event.getEventType(), event.getBookId(), event.getTitle(), event.getAuthor());
        } catch (Exception e) {
            log.info(">>> Message is not a JSON BookEventDTO (probably plain String)");
        }
    }
}