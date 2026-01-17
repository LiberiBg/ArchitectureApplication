package esiea.yangnguyen.architectureapplication.config;

import esiea.yangnguyen.architectureapplication.domain.repository.EventPublisherRepository;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * Configuration de test pour mocker les dépendances externes
 */
@TestConfiguration
public class TestConfig {

    /**
     * Mock de EventPublisherRepository pour éviter les connexions Kafka dans les tests
     */
    @Bean
    @Primary
    public EventPublisherRepository mockEventPublisherRepository() {
        return (topic, key, event) -> {
            // Mock : ne fait rien dans les tests
            System.out.println("Mock: Event published to topic " + topic + " with key " + key);
        };
    }
}
