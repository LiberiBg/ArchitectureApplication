package esiea.yangnguyen.architectureapplication.domain.service;

import esiea.yangnguyen.architectureapplication.usecase.dto.MessageInDTO;
import lombok.experimental.UtilityClass;

import static esiea.yangnguyen.architectureapplication.utils.Utils.isNullOrEmpty;

@UtilityClass
public class MessageService {
    public static void validateMessageBeforeSending(MessageInDTO messageInDTO) {
        if (isNullOrEmpty(String.valueOf(messageInDTO.getIdSender()))) {
            throw new IllegalArgumentException("Message id sender cannot bu null or empty");
        }
        if (isNullOrEmpty(String.valueOf(messageInDTO.getIdReceiver()))) {
            throw new IllegalArgumentException("Message id receiver cannot bu null or empty");
        }
        if (isNullOrEmpty(messageInDTO.getContent())) {
            throw new IllegalArgumentException("Message content cannot bu null or empty");
        }
    }
}
