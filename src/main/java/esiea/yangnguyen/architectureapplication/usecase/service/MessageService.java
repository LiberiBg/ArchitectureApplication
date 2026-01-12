package esiea.yangnguyen.architectureapplication.usecase.service;

import esiea.yangnguyen.architectureapplication.domain.entities.User;
import esiea.yangnguyen.architectureapplication.domain.repository.MessageRepository;
import esiea.yangnguyen.architectureapplication.domain.repository.UserRepository;
import esiea.yangnguyen.architectureapplication.exceptions.ItemNotFoundException;
import esiea.yangnguyen.architectureapplication.usecase.dto.MessageCreateDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.MessageOutDTO;
import esiea.yangnguyen.architectureapplication.usecase.mapper.MessageMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MessageOutDTO sendMessage(MessageCreateDTO messageCreateDTO) {
        esiea.yangnguyen.architectureapplication.domain.service.MessageService.validateMessageBeforeSending(messageCreateDTO.getIdReceiver(), messageCreateDTO.getIdSender(), messageCreateDTO.getContent(), messageCreateDTO.getTimestamp());
        User sender = userRepository.findById(messageCreateDTO.getIdSender())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + messageCreateDTO.getIdSender()));
        User receiver = userRepository.findById(messageCreateDTO.getIdReceiver())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + messageCreateDTO.getIdReceiver()));
        return messageRepository.send(MessageMapper.toDomain(messageCreateDTO, sender, receiver));
    }

    public List<MessageOutDTO> findAll() {
        return messageRepository.findAll();
    }

    public Optional<MessageOutDTO> findById(long id) {
        return messageRepository.findById(id);
    }

    public void deleteProductById(Long id) {
        if (findById(id).isEmpty()) throw new ItemNotFoundException("message with id " + id + " not found");
        messageRepository.deleteById(id);
    }

}
