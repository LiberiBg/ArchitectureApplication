package esiea.yangnguyen.architectureapplication.usecase.dto;

public class UserCreateDTO extends UserDTO {
    private String password;

    public UserCreateDTO(long id, String firstName, String lastname, String email, String password) {
        super(id, firstName, lastname, email);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
