package esiea.yangnguyen.architectureapplication.adapters.infrastructure.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import esiea.yangnguyen.architectureapplication.domain.entities.CreatedMessageEvent;
import esiea.yangnguyen.architectureapplication.domain.repository.EventConsumerRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageEventConsumerAdapter implements EventConsumerRepository {

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @KafkaListener(topics = "messages", groupId = "message-consumer-group")
    public void consume(String message) {
        try {
            CreatedMessageEvent event = objectMapper.readValue(message, CreatedMessageEvent.class);
            System.out.println("New message: " + event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}