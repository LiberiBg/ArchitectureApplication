package esiea.yangnguyen.architectureapplication.domain.repository;

import esiea.yangnguyen.architectureapplication.domain.entities.Message;
import esiea.yangnguyen.architectureapplication.usecase.dto.MessageOutDTO;

import java.util.List;
import java.util.Optional;

public interface MessageRepository {
    MessageOutDTO send(Message message);

    List<MessageOutDTO> findAll();

    Optional<MessageOutDTO> findById(long id);

    void deleteById(long id);

    List<MessageOutDTO> findBySenderId(long senderId);

    List<MessageOutDTO> findByReceiverId(long receiverId);
}
