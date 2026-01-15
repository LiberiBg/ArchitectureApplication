package esiea.yangnguyen.architectureapplication.domain.repository;

import esiea.yangnguyen.architectureapplication.domain.entities.Message;

import java.util.List;
import java.util.Optional;

public interface MessageRepository {
    Message send(Message message);

    List<Message> findAll();

    Optional<Message> findById(long id);

    void deleteById(long id);

    List<Message> findBySenderId(long senderId);

    List<Message> findByReceiverId(long receiverId);
}
