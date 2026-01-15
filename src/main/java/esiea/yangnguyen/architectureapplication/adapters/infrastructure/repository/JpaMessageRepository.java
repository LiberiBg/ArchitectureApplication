package esiea.yangnguyen.architectureapplication.adapters.infrastructure.repository;

import esiea.yangnguyen.architectureapplication.adapters.infrastructure.entity.JpaMessageEntity;
import esiea.yangnguyen.architectureapplication.adapters.infrastructure.entity.JpaUserEntity;
import esiea.yangnguyen.architectureapplication.adapters.infrastructure.mapper.JpaMessageMapper;
import esiea.yangnguyen.architectureapplication.domain.entities.Message;
import esiea.yangnguyen.architectureapplication.domain.repository.MessageRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class JpaMessageRepository implements MessageRepository {

    private final SpringDataMessageRepository springDataMessageRepository;
    private final SpringDataUserRepository springDataUserRepository;

    @Override
    public List<Message> findAll() {
        return springDataMessageRepository.findAll()
                .stream().map(JpaMessageMapper::toDomain)
                .toList();
    }

    @Override
    public Message send(Message message) {
        JpaMessageEntity jpaMessageEntity = JpaMessageMapper.toEntity(message);
        return JpaMessageMapper.toDomain(springDataMessageRepository.save(jpaMessageEntity));
    }

    @Override
    public Optional<Message> findById(long id) {
        return springDataMessageRepository.findById(id)
                .map(JpaMessageMapper::toDomain);
    }

    @Override
    public void deleteById(long id) {
        springDataMessageRepository.deleteById(id);
    }

    @Override
    public List<Message> findBySenderId(long senderId) {
        JpaUserEntity sender = springDataUserRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + senderId));
        return springDataMessageRepository.findBySender(sender)
                .stream()
                .map(JpaMessageMapper::toDomain)
                .toList();
    }

    @Override
    public List<Message> findByReceiverId(long receiverId) {
        JpaUserEntity receiver = springDataUserRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + receiverId));
        return springDataMessageRepository.findByReceiver(receiver)
                .stream()
                .map(JpaMessageMapper::toDomain)
                .toList();
    }
}
