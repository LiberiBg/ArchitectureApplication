package esiea.yangnguyen.architectureapplication.domain.repository;

public interface EventConsumerRepository {
    void consume(String message);
}
