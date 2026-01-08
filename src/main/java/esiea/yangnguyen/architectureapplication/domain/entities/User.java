package esiea.yangnguyen.architectureapplication.domain.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    private long id;
    private String firstName;
    private String lastname;
    private String email;
    private String password;

    public User(long id, String firstName, String lastname, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }

    public User(String firstName, String lastname, String email, String password) {
        this.firstName = firstName;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
    }
}
