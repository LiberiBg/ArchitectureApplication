package esiea.yangnguyen.architectureapplication.adapters.infrastructure.mapper;


import esiea.yangnguyen.architectureapplication.adapters.infrastructure.entity.JpaMessageEntity;
import esiea.yangnguyen.architectureapplication.domain.entities.Message;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JpaMessageMapper {

    public static Message toDomain(esiea.yangnguyen.architectureapplication.adapters.infrastructure.entity.JpaMessageEntity jpaMessage) {
        if (jpaMessage == null) {
            return null;

        }
        return new Message(
                jpaMessage.getId(),
                JpaUserMapper.toDomain(jpaMessage.getSender()),
                JpaUserMapper.toDomain(jpaMessage.getReceiver()),
                jpaMessage.getContent(),
                jpaMessage.getTimestamp()
        );
    }

    public static JpaMessageEntity toEntity(Message message) {
        if (message == null) {
            return null;
        }
        return JpaMessageEntity.builder().id(message.getId()).sender(JpaUserMapper.toEntity(message.getSenderId())).receiver(JpaUserMapper.toEntity(message.getReceiverId())).content(message.getContent()).timestamp(message.getTimestamp()).build();
    }
}