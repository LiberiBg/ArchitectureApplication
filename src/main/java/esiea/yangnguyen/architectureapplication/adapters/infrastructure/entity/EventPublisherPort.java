package esiea.yangnguyen.architectureapplication.adapters.infrastructure.entity;

public interface EventPublisherPort {
    void publish(String topic, String key, Object event);
}
