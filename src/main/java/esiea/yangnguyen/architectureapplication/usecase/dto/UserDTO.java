package esiea.yangnguyen.architectureapplication.usecase.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private long id;
    private String firstName;
    private String lastname;
    private String email;

    public UserDTO(long id, String firstName, String lastname, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastname = lastname;
        this.email = email;
    }
}
