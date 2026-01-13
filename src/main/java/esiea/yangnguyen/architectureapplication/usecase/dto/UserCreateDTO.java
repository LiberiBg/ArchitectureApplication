package esiea.yangnguyen.architectureapplication.usecase.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserCreateDTO {
    private String firstName;
    private String lastName;
    @Schema(example = "test@mail.com")
    private String email;
    @Schema(example = "Password1234*")
    private String password;
}
