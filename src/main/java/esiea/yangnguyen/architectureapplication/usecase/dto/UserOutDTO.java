package esiea.yangnguyen.architectureapplication.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserOutDTO {
    private long id;
    private String firstName;
    private String lastname;
    private String email;
}
