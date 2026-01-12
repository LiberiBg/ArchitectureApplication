package esiea.yangnguyen.architectureapplication.domain.repository;

import esiea.yangnguyen.architectureapplication.domain.entities.Message;
import esiea.yangnguyen.architectureapplication.usecase.dto.MessageOutDTO;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository {
    MessageOutDTO send(Message message);

    List<MessageOutDTO> findAll();

    Optional<MessageOutDTO> findById(long id);

    void deleteById(long id);
}
