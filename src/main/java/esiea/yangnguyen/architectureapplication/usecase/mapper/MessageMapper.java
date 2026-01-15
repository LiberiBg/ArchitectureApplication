package esiea.yangnguyen.architectureapplication.usecase.mapper;

import esiea.yangnguyen.architectureapplication.domain.entities.CreatedMessageEvent;
import esiea.yangnguyen.architectureapplication.domain.entities.Message;
import esiea.yangnguyen.architectureapplication.domain.entities.User;
import esiea.yangnguyen.architectureapplication.usecase.dto.MessageInDTO;
import esiea.yangnguyen.architectureapplication.usecase.dto.MessageOutDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageMapper {
    public static Message toDomain(MessageInDTO messageInDTO, User sender, User receiver, String timestamp) {
        return new Message(
                sender,
                receiver,
                messageInDTO.getContent(),
                timestamp
        );
    }

    public static MessageOutDTO toDTO(Message message) {
        return new MessageOutDTO(
                message.getId(),
                message.getSenderId().getId(),
                message.getReceiverId().getId(),
                message.getContent(),
                message.getTimestamp()
        );
    }

    public static CreatedMessageEvent toEvent(Message message) {
        return new CreatedMessageEvent(
                message.getId(),
                message.getSenderId().getId(),
                message.getReceiverId().getId(),
                message.getContent(),
                message.getTimestamp()
        );
    }
}