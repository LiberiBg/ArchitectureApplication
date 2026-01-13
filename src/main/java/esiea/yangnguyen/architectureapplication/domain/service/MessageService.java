package esiea.yangnguyen.architectureapplication.domain.service;

import lombok.experimental.UtilityClass;

import static esiea.yangnguyen.architectureapplication.utils.Utils.isNullOrEmpty;

@UtilityClass
public class MessageService {
    public static void validateMessageBeforeSending(long idReceiver, long idSender, String content) {
        if (isNullOrEmpty(String.valueOf(idSender))) {
            throw new IllegalArgumentException("Message id receiver cannot bu null or empty");
        }
        if (isNullOrEmpty(String.valueOf(idReceiver))) {
            throw new IllegalArgumentException("Message id receiver cannot bu null or empty");
        }
        if (isNullOrEmpty(content)) {
            throw new IllegalArgumentException("Message content cannot bu null or empty");
        }
    }
}
