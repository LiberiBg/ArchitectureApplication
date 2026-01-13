package esiea.yangnguyen.architectureapplication.adapters.infrastructure.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatedMessageEvent {
    private long id;
    private long senderId;
    private long receiverId;
    private String content;
    private String timestamp;
}
