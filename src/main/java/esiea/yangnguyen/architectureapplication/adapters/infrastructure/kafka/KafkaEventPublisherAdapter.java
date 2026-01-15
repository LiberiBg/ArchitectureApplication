package esiea.yangnguyen.architectureapplication.adapters.infrastructure.kafka;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import esiea.yangnguyen.architectureapplication.adapters.infrastructure.repository.EventPublisherRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventPublisherAdapter implements EventPublisherRepository {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public KafkaEventPublisherAdapter(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(String topic, String key, Object event) {
        try {
            String payload = objectMapper.writeValueAsString(event);
            kafkaTemplate.send(topic, key, payload);
            System.out.println("Payload envoyé :" + payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Erreur sérialisation événement", e);
        }
    }
}
