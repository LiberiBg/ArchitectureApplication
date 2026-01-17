package esiea.yangnguyen.architectureapplication.adapters.infrastructure.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import esiea.yangnguyen.architectureapplication.domain.entities.CreatedProductEvent;
import esiea.yangnguyen.architectureapplication.domain.repository.EventConsumerRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaProductEventConsumerAdapter implements EventConsumerRepository {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "products", groupId = "product-consumer-group")
    public void consume(String message) {
        try {
            CreatedProductEvent event = objectMapper.readValue(message, CreatedProductEvent.class);
            System.out.println("New published product: " + event);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}