package esiea.yangnguyen.architectureapplication.usecase.service;

import esiea.yangnguyen.architectureapplication.adapters.infrastructure.exceptions.MessageNotFoundException;
import esiea.yangnguyen.architectureapplication.adapters.infrastructure.exceptions.UserNotFoundException;
import esiea.yangnguyen.architectureapplication.domain.repository.EventPublisherRepository;
import esiea.yangnguyen.architectureapplication.domain.entities.CreatedMessageEvent;
import esiea.yangnguyen.architectureapplication.domain.entities.Message;
import esiea.yangnguyen.architectureapplication.domain.entities.User;
import esiea.yangnguyen.architectureapplication.domain.repository.MessageRepository;
import esiea.yangnguyen.architectureapplication.domain.repository.UserRepository;
import esiea.yangnguyen.architectureapplication.adapters.infrastructure.exceptions.ItemNotFoundException;
import esiea.yangnguyen.architectureapplication.usecase.dto.MessageInDTO;
import esiea.yangnguyen.architectureapplication.usecase.mapper.MessageMapper;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final EventPublisherRepository eventPublisherRepository;

    public Message sendMessage(MessageInDTO messageInDTO) {
        // Validation métier
        esiea.yangnguyen.architectureapplication.domain.service.MessageService
                .validateMessageBeforeSending(messageInDTO);

        // Récupération users
        User sender = userRepository.findById(messageInDTO.getIdSender())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + messageInDTO.getIdSender()));
        User receiver = userRepository.findById(messageInDTO.getIdReceiver())
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + messageInDTO.getIdReceiver()));

        // Génération timestamp
        String timestamp = LocalDateTime.now().toString();

        // Envoi via repository
        Message message = messageRepository.send(MessageMapper.toDomain(messageInDTO, sender, receiver, timestamp));

        // Publication événement Kafka
        CreatedMessageEvent event = MessageMapper.toEvent(message);

        eventPublisherRepository.publish("messages", String.valueOf(message.getId()), event);

        return message;
    }

    public List<Message> findAll() {
        return messageRepository.findAll().stream().toList();
    }

    public Message findById(long id) {
        return messageRepository.findById(id)
                .orElseThrow(() -> new MessageNotFoundException("Message with id " + id + " not found"));
    }

    public void deleteProductById(Long id) {
        findById(id); // Vérification que le message existe
        messageRepository.deleteById(id);
    }

    public List<Message> findMessagesSentByUser(Long userId) {
        // Vérification que l'utilisateur existe
        userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return messageRepository.findBySenderId(userId).stream().toList();
    }

    public List<Message> findMessagesReceivedByUser(Long userId) {
        // Vérification que l'utilisateur existe
        userRepository.findById(userId)
                .orElseThrow(() -> new ItemNotFoundException("User with id " + userId + " not found"));
        return messageRepository.findByReceiverId(userId).stream().toList();
    }
}

