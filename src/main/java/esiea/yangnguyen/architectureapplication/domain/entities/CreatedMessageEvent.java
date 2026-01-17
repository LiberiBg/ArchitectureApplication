package esiea.yangnguyen.architectureapplication.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CreatedMessageEvent {
    private long id;
    private long senderId;
    private long receiverId;
    private String content;
    private String timestamp;
}
