package esiea.yangnguyen.architectureapplication.adapters.infrastructure.repository;

import esiea.yangnguyen.architectureapplication.adapters.infrastructure.entity.JpaMessageEntity;
import esiea.yangnguyen.architectureapplication.adapters.infrastructure.mapper.JpaMessageMapper;
import esiea.yangnguyen.architectureapplication.domain.entities.Message;
import esiea.yangnguyen.architectureapplication.domain.repository.MessageRepository;
import esiea.yangnguyen.architectureapplication.usecase.dto.MessageOutDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Repository
public class JpaMessageRepository implements MessageRepository {

    private final SpringDataMessageRepository springDataMessageRepository;

    @Override
    public List<MessageOutDTO> findAll() {
        return springDataMessageRepository.findAll()
                .stream().map(JpaMessageMapper::toDomain)
                .toList();
    }

    @Override
    public MessageOutDTO send(Message message) {
        JpaMessageEntity jpaMessageEntity = JpaMessageMapper.toEntity(message);
        return JpaMessageMapper.toDomain(springDataMessageRepository.save(jpaMessageEntity));
    }

    @Override
    public Optional<MessageOutDTO> findById(long id) {
        return springDataMessageRepository.findById(id)
                .map(JpaMessageMapper::toDomain);
    }

    @Override
    public void deleteById(long id) {
        springDataMessageRepository.deleteById(id);
    }
}
