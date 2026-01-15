package esiea.yangnguyen.architectureapplication.usecase.service;

import esiea.yangnguyen.architectureapplication.adapters.infrastructure.entity.EventPublisherPort;
import esiea.yangnguyen.architectureapplication.domain.entities.CreatedMessageEvent;
import esiea.yangnguyen.architectureapplication.domain.entities.User;
import esiea.yangnguyen.architectureapplication.domain.repository.MessageRepository;
import esiea.yangnguyen.architectureapplication.domain.repository.UserRepository;
import esiea.yangnguyen.architectureapplication.adapters.infrastructure.exceptions.ItemNotFoundException;
import esiea.yangnguyen.architectureapplication.usecase.dto.MessageCreateDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.MessageOutDTO;
import esiea.yangnguyen.architectureapplication.usecase.mapper.MessageMapper;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final EventPublisherPort eventPublisherPort;

    public MessageOutDTO sendMessage(MessageCreateDTO messageCreateDTO) {
        // Validation métier
        esiea.yangnguyen.architectureapplication.domain.service.MessageService
                .validateMessageBeforeSending(
                        messageCreateDTO.getIdReceiver(),
                        messageCreateDTO.getIdSender(),
                        messageCreateDTO.getContent()
                );

        // Récupération users
        User sender = userRepository.findById(messageCreateDTO.getIdSender())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + messageCreateDTO.getIdSender()));
        User receiver = userRepository.findById(messageCreateDTO.getIdReceiver())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + messageCreateDTO.getIdReceiver()));

        // Génération timestamp
        String timestamp = LocalDateTime.now().toString();

        // Envoi via repository
        MessageOutDTO messageOut = messageRepository.send(MessageMapper.toDomain(messageCreateDTO, sender, receiver, timestamp));

        // Publication événement Kafka
        CreatedMessageEvent event = new CreatedMessageEvent(
                messageOut.getId(),
                messageCreateDTO.getIdSender(),
                messageCreateDTO.getIdReceiver(),
                messageCreateDTO.getContent(),
                timestamp
        );
        eventPublisherPort.publish("messages", String.valueOf(messageOut.getId()), event);

        return messageOut;
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

    public List<MessageOutDTO> findMessagesSentByUser(Long userId) {
        // Vérification que l'utilisateur existe
        userRepository.findById(userId)
                .orElseThrow(() -> new ItemNotFoundException("User with id " + userId + " not found"));
        return messageRepository.findBySenderId(userId);
    }

    public List<MessageOutDTO> findMessagesReceivedByUser(Long userId) {
        // Vérification que l'utilisateur existe
        userRepository.findById(userId)
                .orElseThrow(() -> new ItemNotFoundException("User with id " + userId + " not found"));
        return messageRepository.findByReceiverId(userId);
    }
}

