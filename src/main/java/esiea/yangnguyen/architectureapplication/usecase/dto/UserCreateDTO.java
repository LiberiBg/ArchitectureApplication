package esiea.yangnguyen.architectureapplication.usecase.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserCreateDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
}
