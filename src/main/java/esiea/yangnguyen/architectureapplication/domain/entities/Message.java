package esiea.yangnguyen.architectureapplication.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Message {
    private long id;
    private User senderId;
    private User receiverId;
    private String content;
    private String timestamp;

    public Message(User sender, User receiver, String content, String timestamp) {
        this.senderId = sender;
        this.receiverId = receiver;
        this.content = content;
        this.timestamp = timestamp;
    }
}
