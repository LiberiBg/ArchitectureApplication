package esiea.yangnguyen.architectureapplication.usecase.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Schema(description = "DTO pour la création d'un message entre utilisateurs")
public class MessageCreateDTO {
    private long idSender;
    private long idReceiver;
    private String content;
}
