package esiea.yangnguyen.architectureapplication.domain.repository;

public interface EventPublisherRepository {
    void publish(String topic, String key, Object event);
}
