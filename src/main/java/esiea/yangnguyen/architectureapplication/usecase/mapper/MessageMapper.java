package esiea.yangnguyen.architectureapplication.usecase.mapper;

import esiea.yangnguyen.architectureapplication.domain.entities.Message;
import esiea.yangnguyen.architectureapplication.domain.entities.User;
import esiea.yangnguyen.architectureapplication.usecase.dto.MessageCreateDTO;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MessageMapper {
    public static Message toDomain(MessageCreateDTO messageCreateDTO, User sender, User receiver) {
        return new Message(
                sender,
                receiver,
                messageCreateDTO.getContent(),
                messageCreateDTO.getContent()
        );
    }
}