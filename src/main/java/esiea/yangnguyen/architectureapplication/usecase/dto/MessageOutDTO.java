package esiea.yangnguyen.architectureapplication.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class MessageOutDTO {
    private long id;
    private long idSender;
    private long idReceiver;
    private String content;
    private String timestamp;
}
