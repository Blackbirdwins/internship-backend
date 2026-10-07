package com.internship.books_api.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.internship.books_api.dto.BookEventDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaProducerService {

    private static final String TOPIC = "books-events";

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = new ObjectMapper();
    }

    public void sendMessage(String message) {
        kafkaTemplate.send(TOPIC, message);
        log.info(">>> Kafka message sent: {}", message);
    }

    public void sendBookEventAsJson(BookEventDTO event) {
        try {
            String json = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(TOPIC, json);
            log.info(">>> Kafka JSON message sent: {}", json);
        } catch (Exception e) {
            log.error("Failed to send JSON", e);
        }
    }
}