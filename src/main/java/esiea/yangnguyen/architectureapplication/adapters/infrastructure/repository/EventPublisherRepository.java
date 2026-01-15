package esiea.yangnguyen.architectureapplication.adapters.infrastructure.repository;

public interface EventPublisherRepository {
    void publish(String topic, String key, Object event);
}
